package br.com.fiap.wavemail.domain.dto.user;

import br.com.fiap.wavemail.domain.model.UserEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserReturnDto(

        UUID id,
        String name,
        String email,
        LocalDateTime createdAt
) {
    public UserReturnDto(UserEntity user){
        this(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }
}
