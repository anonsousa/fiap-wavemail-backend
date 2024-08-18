package br.com.fiap.wavemail.domain.dto.email;

import br.com.fiap.wavemail.domain.enums.EmailPriority;
import br.com.fiap.wavemail.domain.enums.EmailType;
import br.com.fiap.wavemail.domain.model.EmailEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record EmailReturnSendDto(

        UUID id,
        String from,
        List<String> to,
        String subject,
        String body,
        LocalDateTime date,
        EmailPriority priority
) {

    public EmailReturnSendDto(EmailEntity email) {
        this(
                email.getId(),
                email.getFrom(),
                email.getTo(),
                email.getSubject(),
                email.getBody(),
                email.getDate(),
                email.getPriority()
        );
    }

}

