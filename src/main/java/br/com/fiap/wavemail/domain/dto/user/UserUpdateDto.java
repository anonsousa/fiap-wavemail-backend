package br.com.fiap.wavemail.domain.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateDto(

        @NotBlank
        @Size(min = 3, max = 80)
        String name,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String currentPassword,
        @NotBlank
        String newPassword
) {
}
