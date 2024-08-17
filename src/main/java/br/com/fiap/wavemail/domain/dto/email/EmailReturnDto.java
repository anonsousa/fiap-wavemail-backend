package br.com.fiap.wavemail.domain.dto.email;

import br.com.fiap.wavemail.domain.enums.EmailPriority;
import br.com.fiap.wavemail.domain.enums.EmailType;
import br.com.fiap.wavemail.domain.model.EmailEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public record EmailReturnDto(

        UUID id,
        String from,
        String to,
        String subject,
        String body,
        LocalDateTime date,
        EmailType type,
        EmailPriority priority,
        boolean isRead,
        boolean isSent
) {

    public EmailReturnDto(EmailEntity email) {
        this(
                email.getId(),
                email.getFrom(),
                email.getTo(),
                email.getSubject(),
                email.getBody(),
                email.getDate(),
                email.getType(),
                email.getPriority(),
                email.isRead(),
                email.isSent()
        );
    }

}
