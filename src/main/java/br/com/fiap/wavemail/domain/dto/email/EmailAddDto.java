package br.com.fiap.wavemail.domain.dto.email;

import br.com.fiap.wavemail.domain.enums.EmailPriority;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record EmailAddDto(


        @NotNull
        @Size(min = 1)
        List<@NotBlank @Email String> to,

        @NotBlank
        @Size(min = 2, max = 360)
        String subject,
        @NotBlank
        String body,
        @NotNull
        EmailPriority priority

) { }
