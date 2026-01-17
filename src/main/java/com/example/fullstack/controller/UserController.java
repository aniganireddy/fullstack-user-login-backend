package com.example.fullstack.controller;

import com.example.fullstack.common.request.LoginReq;
import com.example.fullstack.common.request.RegisterReq;
import com.example.fullstack.common.response.RegisterRes;
import com.example.fullstack.common.response.UserRes;
import com.example.fullstack.model.User;
import com.example.fullstack.repository.UserRepository;
import com.example.fullstack.security.JwtUtil;
import com.example.fullstack.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.dnd.DragSourceMotionListener;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    public UserController(UserService userService, UserRepository userRepository, JwtUtil jwtUtil) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public RegisterRes register(@RequestBody RegisterReq user) {
        return userService.register(user);
    }

    @GetMapping("/getAll")
    public List<UserRes> getAll(){
        return userService.getAll();
    }

    @DeleteMapping("/delete/{partyId}")
    public ResponseEntity<String> delete(@PathVariable String partyId) {

        userService.deleteUser(partyId);

        return ResponseEntity.ok("User deleted successfully");
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginReq req) {
        return userService.login(req);

    }
}



