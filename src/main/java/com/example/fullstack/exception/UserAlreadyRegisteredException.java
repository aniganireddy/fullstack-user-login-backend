package com.example.fullstack.exception;

public class UserAlreadyRegisteredException extends RuntimeException{
    public UserAlreadyRegisteredException(String userAlreadyExists) {
    }
}
