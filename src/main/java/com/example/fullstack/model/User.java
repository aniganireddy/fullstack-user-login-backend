package com.example.fullstack.model;

import com.example.fullstack.enums.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "user")
public class User {
    @Id
    private String id;
    private String userName;
    private String email;
    private String password;
    private Role role;
    private String partyId;


}
