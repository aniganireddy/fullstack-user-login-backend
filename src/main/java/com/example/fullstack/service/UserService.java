package com.example.fullstack.service;


import com.example.fullstack.common.request.LoginReq;
import com.example.fullstack.common.request.RegisterReq;
import com.example.fullstack.common.response.RegisterRes;
import com.example.fullstack.common.response.UserRes;
import com.example.fullstack.enums.Role;
import com.example.fullstack.exception.EmailOrPasswordNotMatchedException;
import com.example.fullstack.exception.UserAlreadyRegisteredException;
import com.example.fullstack.exception.UserNotExistsException;
import com.example.fullstack.model.User;
import com.example.fullstack.repository.UserRepository;
import com.example.fullstack.security.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }


    public RegisterRes register(RegisterReq req) {

        String email = req.getEmail();

        Optional<User> existing = userRepository.findByEmail(email);

        if (existing.isPresent()){
            throw new UserAlreadyRegisteredException("User Already Exists");
        }

        User savedUser = new User();
        savedUser.setUserName(req.getUserName());
        savedUser.setEmail(req.getEmail());
        savedUser.setPassword(passwordEncoder.encode(req.getPassword()));
        savedUser.setRole(Role.USER);
        savedUser.setPartyId(generatePartyId());
        log.info("User registered successfully for this email : {}" , email);
        userRepository.save(savedUser);
return RegisterRes.builder()
        .email(savedUser.getEmail())
        .userName(savedUser.getUserName())
        .role(savedUser.getRole())
        .partyId(savedUser.getPartyId())
        .build();

    }

    public List<UserRes> getAll(){
        List<User> allUsers= userRepository.findAll();
        if (allUsers.isEmpty()){
            throw new UserNotExistsException();
        }
        return allUsers.stream().map(user -> UserRes.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .role(user.getRole())
                .partyId(user.getPartyId())
                .build()

        ).toList();
    }



    public Optional<User> deleteUser(String partyId){

       Optional<User> existing= userRepository.findByPartyId(partyId);
       if (!existing.isPresent()){
           throw new UserNotExistsException();
       }
       log.info("deleting the requested user : {}" ,partyId);
        return  userRepository.deleteByPartyId(partyId);
    }



    public String login(LoginReq req) {

        User existingUser = userRepository.findByEmail(req.getEmail())
                .orElseThrow(UserNotExistsException::new);

        if (!passwordEncoder.matches(req.getPassword(), existingUser.getPassword())) {
            throw new EmailOrPasswordNotMatchedException();
        }

        return jwtUtil.generateToken(existingUser.getEmail());
    }

    private String generatePartyId(){
        Random random= new Random();
      int id =1000000 + random.nextInt(9000000);
      return String.valueOf(id);
    }
}
