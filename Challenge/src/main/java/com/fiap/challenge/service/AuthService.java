package com.fiap.challenge.service;

import com.fiap.challenge.dto.LoginRequest;
import com.fiap.challenge.dto.LoginResponse;
import com.fiap.challenge.dto.RegisterRequest;
import com.fiap.challenge.dto.UserResponse;
import com.fiap.challenge.model.Usuario;
import com.fiap.challenge.repository.UsuarioRepository;
import com.fiap.challenge.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    /**
     * Realiza o login do usuário
     * @param loginRequest Dados de login (email e senha)
     * @return LoginResponse contendo o token JWT e os dados do usuário
     */
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest loginRequest) {
        // Autentica o usuário
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getSenha()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Gera o token JWT
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);

        // Busca os dados completos do usuário
        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        UserResponse userResponse = new UserResponse(usuario);

        return new LoginResponse(token, userResponse);
    }

    /**
     * Registra um novo usuário
     * @param registerRequest Dados do novo usuário
     * @return UserResponse com os dados do usuário criado
     */
    @Transactional
    public UserResponse register(RegisterRequest registerRequest) {
        // Verifica se o email já está em uso
        if (usuarioRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email já está em uso");
        }

        // Cria o novo usuário
        Usuario usuario = new Usuario();
        usuario.setNome(registerRequest.getNome());
        usuario.setEmail(registerRequest.getEmail());
        usuario.setSenha(passwordEncoder.encode(registerRequest.getSenha()));
        usuario.setAtivo(true);

        // Salva no banco de dados
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new UserResponse(usuarioSalvo);
    }

    /**
     * Recupera o usuário atualmente autenticado
     * @return UserResponse com os dados do usuário logado
     */
    @Transactional(readOnly = true)
    public UserResponse getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado");
        }

        String email = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return new UserResponse(usuario);
    }
}
