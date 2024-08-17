package br.com.fiap.wavemail.controllers;

import br.com.fiap.wavemail.domain.dto.preferences.UserPreferencesAddDto;
import br.com.fiap.wavemail.domain.dto.preferences.UserPreferencesReturnDto;
import br.com.fiap.wavemail.domain.service.UserPreferencesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user/preferences")
public class UserPreferencesController {

    @Autowired
    private UserPreferencesService userPreferencesService;

    @PostMapping
    public ResponseEntity<UserPreferencesReturnDto> createUserPreferences(@RequestBody @Valid UserPreferencesAddDto userPreferences) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userPreferencesService.createUserPreferences(userPreferences));
    }

    @GetMapping
    public ResponseEntity<UserPreferencesReturnDto> getUserPreferences(@RequestParam("id")UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(userPreferencesService.getUserPreferences(id));
    }

    @PutMapping
    public ResponseEntity<UserPreferencesReturnDto> updateUserPreferences(@RequestParam("id") UUID id,
                                                                          @RequestBody @Valid UserPreferencesAddDto userPreferences) {
        return ResponseEntity.status(HttpStatus.OK).body(userPreferencesService.updateUserPreferences(id, userPreferences));
    }
}
