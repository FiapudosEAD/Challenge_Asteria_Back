package com.fiap.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private String tipo = "Bearer";
    private UserResponse usuario;

    public LoginResponse(String token, UserResponse usuario) {
        this.token = token;
        this.usuario = usuario;
    }
}
