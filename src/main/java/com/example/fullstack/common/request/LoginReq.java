package com.example.fullstack.common.request;

import com.example.fullstack.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LoginReq {

    private String email;
    private String password;
}
