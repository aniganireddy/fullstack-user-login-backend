package com.example.fullstack.model;

import com.example.fullstack.enums.Role;
import com.example.fullstack.enums.Status;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "login")
public class User {
    @Id
    private String id;
    private String userName;
    private String email;
    private Status status;
    private String partyId;
    private LocalDateTime createdAt;



}
