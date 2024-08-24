package br.com.fiap.wavemail.domain.dto.auth;

import br.com.fiap.wavemail.domain.model.UserEntity;

public record TokenDto(
        String email,
        String token) {

    public TokenDto(UserEntity userEntity, String token) {
        this(userEntity.getEmail(), token);
    }
}
