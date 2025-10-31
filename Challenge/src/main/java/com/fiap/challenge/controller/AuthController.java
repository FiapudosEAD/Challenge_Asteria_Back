package com.fiap.challenge.controller;

import com.fiap.challenge.dto.LoginRequest;
import com.fiap.challenge.dto.LoginResponse;
import com.fiap.challenge.dto.RegisterRequest;
import com.fiap.challenge.dto.UserResponse;
import com.fiap.challenge.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    /**
     * Endpoint de login
     * POST /api/auth/login
     *
     * @param loginRequest Dados de login (email e senha)
     * @return LoginResponse com token JWT e dados do usuário
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Endpoint de registro de novo usuário
     * POST /api/auth/register
     *
     * @param registerRequest Dados do novo usuário
     * @return UserResponse com os dados do usuário criado
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            UserResponse response = authService.register(registerRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Endpoint para recuperar o usuário logado
     * GET /api/auth/me
     *
     * Requer autenticação (token JWT no header Authorization)
     *
     * @return UserResponse com os dados do usuário logado
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        try {
            UserResponse response = authService.getCurrentUser();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Endpoint de health check
     * GET /api/auth/health
     *
     * @return String indicando que a API está funcionando
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("API está funcionando!");
    }
}
