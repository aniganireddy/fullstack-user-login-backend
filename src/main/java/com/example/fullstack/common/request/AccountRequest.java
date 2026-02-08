package com.example.fullstack.common.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountRequest {
    private String firstName;
    private String secondName;
    private String lastName;
//    private String fullName;
    private String mobileNumber;
    private String aadharCard;
    private String panCard;
    private String emailId;
    private String branch;
    private LocalDateTime createdAt;

}

