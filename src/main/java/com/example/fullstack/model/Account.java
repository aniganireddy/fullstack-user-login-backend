package com.example.fullstack.model;

import com.example.fullstack.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "Account")
public class Account {

    @Id
    private String id;
    private String firstName;
    private String secondName;
    private String lastName;
    private String fullName;
    private String mobileNumber;
    private String aadharCard;
    private String panCard;
    private String emailId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String partyId;
    private String accountNumber;
    private String branch;
    private AccountStatus status;

}
