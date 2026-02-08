package com.example.fullstack.repository;

import com.example.fullstack.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account,String> {

    Optional<Account> findByPartyId(String partyId);
    Optional<Account> deleteBypartyId(String partyId);

}
