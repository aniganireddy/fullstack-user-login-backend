package com.example.fullstack.controller;

import com.example.fullstack.common.request.RegisterReq;
import com.example.fullstack.common.response.RegisterRes;
import com.example.fullstack.service.UserService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public RegisterRes profile(@AuthenticationPrincipal Jwt jwt) {

        RegisterReq req = new RegisterReq();
        req.setPartyId(jwt.getClaimAsString("partyId"));
        req.setUserName(jwt.getClaimAsString("preferred_username"));
        req.setEmail(jwt.getClaimAsString("email"));

        return userService.addToDb(req);

}

}



