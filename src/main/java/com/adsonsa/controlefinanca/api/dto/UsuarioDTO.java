package com.adsonsa.controlefinanca.api.dto;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private String email;
    private String nome;
    private String senha;
}