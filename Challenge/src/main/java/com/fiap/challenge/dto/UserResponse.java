package com.fiap.challenge.dto;

import com.fiap.challenge.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String nome;
    private String email;
    private LocalDateTime dataCriacao;
    private Boolean ativo;

    public UserResponse(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.dataCriacao = usuario.getDataCriacao();
        this.ativo = usuario.getAtivo();
    }
}
