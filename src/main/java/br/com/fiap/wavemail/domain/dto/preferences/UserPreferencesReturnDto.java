package br.com.fiap.wavemail.domain.dto.preferences;


import br.com.fiap.wavemail.domain.dto.user.UserReturnDto;
import br.com.fiap.wavemail.domain.enums.Themes;
import br.com.fiap.wavemail.domain.model.UserPreferences;

import java.util.UUID;

public record UserPreferencesReturnDto (
    UUID id,
    Themes theme,
    String colorScheme,
    UserReturnDto user
){
    public UserPreferencesReturnDto (UserPreferences userPreferences){
        this(
                userPreferences.getId(),
                userPreferences.getTheme(),
                userPreferences.getColorScheme(),
                new UserReturnDto(userPreferences.getUser())
        );
    }
}
