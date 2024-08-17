package br.com.fiap.wavemail.domain.dto.preferences;

import br.com.fiap.wavemail.domain.enums.Themes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record UserPreferencesAddDto(

        @NotNull
        Themes theme,
        @NotBlank
        @Pattern(regexp = "^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$", message = "Invalid hexadecimal color code")
        String colorScheme,
        @NotNull
        UUID user
) { }
