package br.com.fiap.wavemail.controllers;


import br.com.fiap.wavemail.domain.dto.calendar.CalendarAddDto;
import br.com.fiap.wavemail.domain.dto.calendar.CalendarReturnDto;
import br.com.fiap.wavemail.domain.service.CalendarEventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {
    @Autowired
    private CalendarEventService calendarEventService;

    @PostMapping
    public ResponseEntity<CalendarReturnDto> createEvent(@RequestParam("userId") UUID userId,
                                                         @RequestBody @Valid CalendarAddDto event){
        return ResponseEntity.status(HttpStatus.CREATED).body(calendarEventService.createCalendarEvent(userId, event));
    }

    @GetMapping
    public ResponseEntity<CalendarReturnDto> getCalendarEvent(@RequestParam("eventId") UUID eventId){
        return ResponseEntity.status(HttpStatus.OK).body(calendarEventService.getCalendarEvent(eventId));
    }

    @GetMapping("/user")
    public ResponseEntity<Page<CalendarReturnDto>> getUserCalendarEvent(@RequestParam("userId") UUID userId,
                                                                        Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(calendarEventService.getUserEvents(userId, pageable));
    }

    @PutMapping
    public ResponseEntity<CalendarReturnDto> updateEvent(@RequestParam("userId") UUID userId,
                                                         @RequestParam("eventId") UUID calendarId,
                                                         @RequestBody @Valid CalendarAddDto event){
        return ResponseEntity.status(HttpStatus.OK).body(calendarEventService.updateCalendarEvent(userId, calendarId, event));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteEvent(@RequestParam("eventId") UUID eventId){
        calendarEventService.deleteCalendarEvent(eventId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
