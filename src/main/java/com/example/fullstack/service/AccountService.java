package com.example.fullstack.service;

import com.example.fullstack.common.request.AccountRequest;
import com.example.fullstack.common.request.AccountUpdateReq;
import com.example.fullstack.common.response.AccountResponse;
import com.example.fullstack.common.response.AccountDetailsRes;
import com.example.fullstack.common.response.AccountUpdateRes;
import com.example.fullstack.enums.AccountStatus;
import com.example.fullstack.events.AccountCreationEvent;
import com.example.fullstack.events.AccountUpdateEvent;
import com.example.fullstack.exception.AccountAlreadyExistsException;
import com.example.fullstack.exception.AccountNotExistsException;
import com.example.fullstack.model.Account;
import com.example.fullstack.repository.AccountRepository;
import com.example.fullstack.service.kafka.AccountEventProducer;
import com.example.fullstack.service.kafka.AccountUpdateEventProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountEventProducer accountEventProducer;
    private final AccountUpdateEventProducer accountUpdateEventProducer;

    public AccountService(AccountRepository accountRepository, AccountEventProducer accountEventProducer, AccountUpdateEventProducer accountUpdateEventProducer) {
        this.accountRepository = accountRepository;
        this.accountEventProducer = accountEventProducer;
        this.accountUpdateEventProducer = accountUpdateEventProducer;
    }

    /**
     * Create bank account for a logged-in user.
     * partyId is trusted because it comes from JWT (CIAM).
     */
    public AccountResponse addAccount(String partyId, AccountRequest request) {

        accountRepository.findByPartyId(partyId)
                .ifPresent(acc -> {
                    throw new AccountAlreadyExistsException();
                });

        Account account = new Account();
        account.setPartyId(partyId);
        account.setFirstName(request.getFirstName());
        account.setSecondName(request.getSecondName());
        account.setLastName(request.getLastName());
        account.setFullName(buildFullName(
                request.getFirstName(),
                request.getSecondName(),
                request.getLastName()
        ));
        account.setMobileNumber(request.getMobileNumber());
        account.setEmailId(request.getEmailId());
        account.setPanCard(request.getPanCard());
        account.setAadharCard(request.getAadharCard());
        account.setBranch(request.getBranch());
        account.setAccountNumber(generateAccountNumber(partyId));
        account.setStatus(AccountStatus.ACTIVE);
        account.setCreatedAt(LocalDateTime.now());

        Account savedAccount = accountRepository.save(account);

        AccountCreationEvent event = new AccountCreationEvent(
                partyId,
                savedAccount.getAccountNumber(),
                savedAccount.getFullName(),
                savedAccount.getEmailId(),
                savedAccount.getMobileNumber()
        );

        accountEventProducer.sendEvent(event);



        return AccountResponse.builder()
                .accountNumber(savedAccount.getAccountNumber())
                .fullName(savedAccount.getFullName())
                .mobileNumber(savedAccount.getMobileNumber())
                .emailId(savedAccount.getEmailId())
                .createdAt(savedAccount.getCreatedAt())
                .status(savedAccount.getStatus())
                .build();
    }

    public AccountDetailsRes getAccount(String partyId) {

        Account dbUser = accountRepository.findByPartyId(partyId)
                .orElseThrow(AccountNotExistsException::new);

        return AccountDetailsRes.builder()
                .aadharCard(dbUser.getAadharCard())
                .branch(dbUser.getBranch())
                .panCard(dbUser.getPanCard())
                .secondName(dbUser.getSecondName())
                .lastName(dbUser.getLastName())
                .firstName(dbUser.getFirstName())
                .emailId(dbUser.getEmailId())
                .fullName(dbUser.getFullName())
                .mobileNumber(dbUser.getMobileNumber())
                .build();
    }

    public AccountUpdateRes updateAccountDetails(String partyId, AccountUpdateReq request) {

        Account dbRec = accountRepository.findByPartyId(partyId)
                .orElseThrow(AccountNotExistsException::new);

        dbRec.setFirstName(request.getFirstName());
        dbRec.setSecondName(request.getSecondName());
        dbRec.setLastName(request.getLastName());
        dbRec.setFullName(buildFullName(
                request.getFirstName(),
                request.getSecondName(),
                request.getLastName()
        ));
        dbRec.setMobileNumber(request.getMobileNumber());
        dbRec.setEmailId(request.getEmailId());
        dbRec.setUpdatedAt(LocalDateTime.now());

        Account updated = accountRepository.save(dbRec);

        AccountUpdateEvent event = new AccountUpdateEvent(partyId, updated.getFirstName(), updated.getSecondName(), updated.getLastName(), updated.getFullName(), updated.getMobileNumber(),updated.getEmailId());

        accountUpdateEventProducer.sendUpdateEvent(event);

        return AccountUpdateRes.builder()
                .fullName(updated.getFullName())
                .mobileNumber(updated.getMobileNumber())
                .emailId(updated.getEmailId())
                .firstName(updated.getFirstName())
                .secondName(updated.getSecondName())
                .lastName(updated.getLastName())
                .updatedAt(updated.getUpdatedAt())
                .build();
    }


    public List<AccountDetailsRes> getAll() {
        List<Account> allRecords = accountRepository.findAll();
        return allRecords.stream().map(acc -> AccountDetailsRes.builder()
                .mobileNumber(acc.getMobileNumber())
                .firstName(acc.getFirstName())
                .secondName(acc.getSecondName())
                .lastName(acc.getLastName())
                .branch(acc.getBranch())
                .panCard(acc.getPanCard())
                .fullName(acc.getFullName())
                .emailId(acc.getEmailId())
                .aadharCard(acc.getAadharCard())
                .createdAt(acc.getCreatedAt())
                .build()).toList();


    }


    public String deleteById(String partyid) {

        Account existsRec = accountRepository.findByPartyId(partyid).orElseThrow(AccountNotExistsException::new);

        log.info("deleting the account record for partyId : {}", partyid);
        accountRepository.delete(existsRec);
        return "Deleted Successfully for partyId = " + partyid;


    }

    public String deleteAll() {

//        Account existsRec = accountRepository.findByPartyId(partyid).orElseThrow(AccountNotExistsException::new);

        log.info("deleting the all account records");
        accountRepository.deleteAll();
        return "Deleted Successfully for all records";


    }

    /**
     * Generates unique account number.
     * (Replace with DB sequence in real banking systems)
     */
    private String generateAccountNumber(String partyId) {
        return "AB" + partyId + "BK";
    }

    /**
     * Builds properly formatted full name
     */
    private String buildFullName(String firstName, String secondName, String lastName) {
        return String.join(" ",
                firstName,
                secondName,
                lastName);
    }
}
