package br.com.fiap.wavemail.domain.service;

import br.com.fiap.wavemail.domain.dto.calendar.CalendarAddDto;
import br.com.fiap.wavemail.domain.dto.calendar.CalendarReturnDto;
import br.com.fiap.wavemail.domain.model.CalendarEventEntity;
import br.com.fiap.wavemail.domain.model.UserEntity;
import br.com.fiap.wavemail.domain.repository.CalendarEventEntityRepository;
import br.com.fiap.wavemail.domain.repository.UserEntityRepository;
import br.com.fiap.wavemail.infra.exceptions.ItemNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
public class CalendarEventService {
    @Autowired
    private CalendarEventEntityRepository calendarRepository;

    @Autowired
    private UserEntityRepository userRepository;

    @Transactional
    public CalendarReturnDto createCalendarEvent(UUID userId, CalendarAddDto event) {
        CalendarEventEntity calendarEventEntity = new CalendarEventEntity();
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ItemNotFoundException("User not found!"));

        BeanUtils.copyProperties(event, calendarEventEntity);

        List<String> participants = calendarEventEntity.getParticipantsEmails();
        if (participants == null) {
            participants = new ArrayList<>();
        }

        HashSet<String> uniqueParticipants = new HashSet<>(participants);
        uniqueParticipants.add(userEntity.getEmail());

        calendarEventEntity.setParticipantsEmails(new ArrayList<>(uniqueParticipants));

        return new CalendarReturnDto(calendarRepository.save(calendarEventEntity));
    }

    @Transactional(readOnly = true)
    public CalendarReturnDto getCalendarEvent(UUID calendarEventId) {
        CalendarEventEntity calendarEvent = calendarRepository.findById(calendarEventId)
                .orElseThrow(() -> new ItemNotFoundException("Calendar not found!"));

        return new CalendarReturnDto(calendarEvent);
    }

    @Transactional(readOnly = true)
    public Page<CalendarReturnDto> getUserEvents(UUID userId, Pageable pageable) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ItemNotFoundException("User not found!"));

        Page<CalendarEventEntity> events = calendarRepository.findByParticipantEmail(userEntity.getEmail(), pageable);

        return events.map(CalendarReturnDto::new);
    }

    @Transactional
    public CalendarReturnDto updateCalendarEvent(UUID userId, UUID calendarEventId, CalendarAddDto event) {
        CalendarEventEntity eventEntity = calendarRepository.findById(calendarEventId)
                .orElseThrow(() -> new ItemNotFoundException("Event not found!"));

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ItemNotFoundException("User not found!") );

        if (eventEntity.getParticipantsEmails().contains(userEntity.getEmail())) {
            BeanUtils.copyProperties(event, eventEntity);
            List<String> participants = eventEntity.getParticipantsEmails();
            HashSet<String> uniqueParticipants = new HashSet<>(participants);
            uniqueParticipants.add(userEntity.getEmail());

            eventEntity.setParticipantsEmails(new ArrayList<>(uniqueParticipants));

            return new CalendarReturnDto(calendarRepository.save(eventEntity));

        } else {
            throw new ItemNotFoundException("User not allowed to update this event!");
        }
    }

    @Transactional
    public void deleteCalendarEvent(UUID calendarEventId) {
        CalendarEventEntity calendarEvent = calendarRepository.findById(calendarEventId)
                .orElseThrow(() -> new ItemNotFoundException("Calendar not found!"));

        calendarRepository.delete(calendarEvent);
    }
}
