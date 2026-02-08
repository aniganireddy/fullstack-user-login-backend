package com.example.fullstack.controller;

import com.example.fullstack.common.request.AccountRequest;
import com.example.fullstack.common.request.AccountUpdateReq;
import com.example.fullstack.common.response.AccountResponse;
import com.example.fullstack.common.response.AccountDetailsRes;
import com.example.fullstack.common.response.AccountUpdateRes;
import com.example.fullstack.model.Account;
import com.example.fullstack.service.AccountService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

@RequestMapping("/user")
@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public AccountResponse createAccount(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody AccountRequest accountRequest) {

        String partyId = jwt.getClaimAsString("partyId");
        return accountService.addAccount(partyId, accountRequest);
    }


    @GetMapping("/get")
    public AccountDetailsRes getAccount( @AuthenticationPrincipal Jwt jwt){
        String partyId = jwt.getClaimAsString("partyId");
        return accountService.getAccount(partyId);
    }


    @PutMapping("/update")
    public AccountUpdateRes updateAccount(@AuthenticationPrincipal Jwt jwt, @RequestBody AccountUpdateReq req){
        String partyId = jwt.getClaimAsString("partyId");
        return accountService.updateAccountDetails(partyId,req);
    }

    @GetMapping("/get/all")

    public List<AccountDetailsRes> getAll( @AuthenticationPrincipal Jwt jwt){
        String partyId = jwt.getClaimAsString("partyId");
        return accountService.getAll();
    }



    @DeleteMapping("/delete")

    public String deleteById( @AuthenticationPrincipal Jwt jwt){
        String partyId = jwt.getClaimAsString("partyId");
        return accountService.deleteById(partyId);
    }

    @DeleteMapping("/delete/all")

    public String deleteAll( @AuthenticationPrincipal Jwt jwt){
        String partyId = jwt.getClaimAsString("partyId");
        return accountService.deleteAll();
    }

}
