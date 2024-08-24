package br.com.fiap.wavemail.domain.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthUserLoginDto(

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(max = 20, min = 8)
        String password
) {
}
