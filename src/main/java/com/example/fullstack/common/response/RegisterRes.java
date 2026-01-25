package com.example.fullstack.common.response;

import com.example.fullstack.enums.Role;
import com.example.fullstack.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterRes {
    private String userName;
    private String email;
    private String partyId;
    private Status userStatus;


}
