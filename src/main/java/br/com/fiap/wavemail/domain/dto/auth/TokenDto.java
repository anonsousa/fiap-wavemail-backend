package br.com.fiap.wavemail.domain.dto.auth;

import br.com.fiap.wavemail.domain.model.UserEntity;

import java.util.UUID;

public record TokenDto(
        UUID userId,
        String email,
        String token) {

    public TokenDto(UserEntity userEntity, String token) {
        this(userEntity.getId(), userEntity.getEmail(), token);
    }
}
