package br.com.fiap.wavemail.domain.service;

import br.com.fiap.wavemail.domain.dto.preferences.UserPreferencesAddDto;
import br.com.fiap.wavemail.domain.dto.preferences.UserPreferencesReturnDto;
import br.com.fiap.wavemail.domain.dto.user.UserReturnDto;
import br.com.fiap.wavemail.domain.model.UserEntity;
import br.com.fiap.wavemail.domain.model.UserPreferences;
import br.com.fiap.wavemail.domain.repository.UserEntityRepository;
import br.com.fiap.wavemail.domain.repository.UserPreferencesRepository;
import br.com.fiap.wavemail.infra.exceptions.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserPreferencesService {

    @Autowired
    private UserPreferencesRepository userPreferencesRepository;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Transactional
    public UserPreferencesReturnDto createUserPreferences(UserPreferencesAddDto userPreferences) {
        UserEntity userEntity = userEntityRepository.findById(userPreferences.user()).orElseThrow(() -> new ItemNotFoundException("User not found"));
        UserPreferences userPreferencesEntity = new UserPreferences();

        userPreferencesEntity.setTheme(userPreferences.theme());
        userPreferencesEntity.setColorScheme(userPreferences.colorScheme());
        userPreferencesEntity.setUser(userEntity);

        return new UserPreferencesReturnDto(userPreferencesRepository.save(userPreferencesEntity));
    }

    @Transactional(readOnly = true)
    public UserPreferencesReturnDto getUserPreferences(UUID id) {
        UserPreferences userPreferences = userPreferencesRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("User not found"));
        return new UserPreferencesReturnDto(userPreferences);
    }

    @Transactional
    public UserPreferencesReturnDto updateUserPreferences(UUID id, UserPreferencesAddDto userPreferences) {
        UserPreferences userPreferencesEntity = userPreferencesRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("User not found"));
        userPreferencesEntity.setTheme(userPreferences.theme());
        userPreferencesEntity.setColorScheme(userPreferences.colorScheme());
        return new UserPreferencesReturnDto(userPreferencesRepository.save(userPreferencesEntity));
    }
}
