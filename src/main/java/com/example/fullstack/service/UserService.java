package com.example.fullstack.service;

import com.example.fullstack.common.request.RegisterReq;
import com.example.fullstack.common.response.RegisterRes;
import com.example.fullstack.enums.Status;
import com.example.fullstack.exception.UserAlreadyRegisteredException;
import com.example.fullstack.model.User;
import com.example.fullstack.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


        public RegisterRes addToDb(RegisterReq req) {
            Optional<User> existingUser = userRepository.findByPartyId(req.getPartyId());

            if (existingUser.isPresent()) {
                User user = existingUser.get();
                return RegisterRes.builder()
                        .partyId(user.getPartyId())
                        .userName(user.getUserName())
                        .email(user.getEmail())
                        .userStatus(user.getStatus())
                        .build();
            }

            User user = new User();
            user.setPartyId(req.getPartyId());
            user.setUserName(req.getUserName());
            user.setEmail(req.getEmail());
            user.setStatus(Status.LOGIN);
            user.setCreatedAt(LocalDateTime.now());

            User savedUser = userRepository.save(user);

            return RegisterRes.builder()
                    .partyId(savedUser.getPartyId())
                    .userName(savedUser.getUserName())
                    .email(savedUser.getEmail())
                    .userStatus(savedUser.getStatus())
                    .build();
        }
    }


