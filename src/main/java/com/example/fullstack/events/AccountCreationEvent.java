package com.example.fullstack.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountCreationEvent {
    private String partyId;
    private String accountNumber;
    private String fullName;
    private String emailId;
    private String mobileNumber;
}
