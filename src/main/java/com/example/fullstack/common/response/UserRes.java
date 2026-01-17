package com.example.fullstack.common.response;

import com.example.fullstack.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserRes {

    private String id;
    private String userName;
    private String email;
//    private String password;
    private Role role;
    private String partyId;

}
