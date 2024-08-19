package br.com.fiap.wavemail.domain.dto.calendar;

import br.com.fiap.wavemail.domain.enums.EventType;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public record CalendarAddDto(

        @NotBlank
        String title,
        @Size(max = 500, message = "Description cannot exceed 500 characters")
        String description,
        @NotNull
        LocalDateTime startTime,
        @NotNull
        LocalDateTime endTime,
        @NotEmpty
        List<@Email String> participantsEmails,
        @NotNull
        EventType eventType
) {
}
