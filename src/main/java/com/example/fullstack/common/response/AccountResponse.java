package com.example.fullstack.common.response;

import com.example.fullstack.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountResponse {

    private String fullName;
    private String mobileNumber;
    private String emailId;
    private String accountNumber;
    private LocalDateTime createdAt;
    private AccountStatus status;

}

