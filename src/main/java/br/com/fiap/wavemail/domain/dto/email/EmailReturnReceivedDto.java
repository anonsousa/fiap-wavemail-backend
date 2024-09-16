package br.com.fiap.wavemail.domain.dto.email;

import br.com.fiap.wavemail.domain.enums.EmailPriority;
import br.com.fiap.wavemail.domain.enums.EmailType;
import br.com.fiap.wavemail.domain.model.EmailEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record EmailReturnReceivedDto(

        UUID id,
        String from,
        List<String> to,
        List<String> cc,
        String subject,
        String body,
        LocalDateTime date,
        EmailType type,
        EmailPriority priority,
        boolean isRead,
        boolean isSent,
        boolean flaged
) {

    public EmailReturnReceivedDto(EmailEntity email) {
        this(
                email.getId(),
                email.getFrom(),
                email.getTo(),
                email.getCc(),
                email.getSubject(),
                email.getBody(),
                email.getDate(),
                email.getType(),
                email.getPriority(),
                email.isRead(),
                email.isSent(),
                email.isFlaged()
        );
    }

}
