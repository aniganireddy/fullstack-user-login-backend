package com.example.fullstack.repository;

import com.example.fullstack.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {


    Optional<User> findByEmail(String email);
    Optional<User> deleteByPartyId(String partyId);
    Optional<User> findByPartyId(String partyId);

}
