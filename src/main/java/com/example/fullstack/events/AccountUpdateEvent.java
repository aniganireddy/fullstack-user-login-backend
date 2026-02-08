package com.example.fullstack.events;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountUpdateEvent {
    private String partyId;
    private String firstName;
    private String secondName;
    private String lastName;
    private String fullName;
    private String mobileNumber;
//    private String aadharCard;
//    private String panCard;
    private String emailId;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
//    private String accountNumber;
//    private String branch;
}
