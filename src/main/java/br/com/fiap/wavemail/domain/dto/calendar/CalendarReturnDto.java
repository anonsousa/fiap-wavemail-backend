package br.com.fiap.wavemail.domain.dto.calendar;

import br.com.fiap.wavemail.domain.enums.EventType;
import br.com.fiap.wavemail.domain.model.CalendarEventEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CalendarReturnDto (
        UUID id,
        String title,
        String description,
        LocalDateTime startTime,
        LocalDateTime endTime,
        List<String> participantsEmails,
        EventType eventType
){
    public CalendarReturnDto(CalendarEventEntity event) {
        this(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getStartTime(),
                event.getEndTime(),
                event.getParticipantsEmails(),
                event.getEventType()
        );
    }
}
