package ru.andrew.hack.biv.auth.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}