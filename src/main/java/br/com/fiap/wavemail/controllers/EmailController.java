package br.com.fiap.wavemail.controllers;

import br.com.fiap.wavemail.domain.dto.email.EmailAddDto;
import br.com.fiap.wavemail.domain.dto.email.EmailReturnReceivedDto;
import br.com.fiap.wavemail.domain.dto.email.EmailReturnSendDto;
import br.com.fiap.wavemail.domain.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/mail")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @GetMapping("/received")
    public ResponseEntity<Page<EmailReturnReceivedDto>> getAllReceivedEmails(@RequestParam("id") UUID id, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(emailService.getReceivedEmails(id, pageable));
    }

    @GetMapping
    public ResponseEntity<EmailReturnReceivedDto> getReceivedEmail(@RequestParam("userId") UUID userId,
                                                                   @RequestParam("mailId") UUID mailId) {
        return ResponseEntity.status(HttpStatus.OK).body(emailService.getReceivedEmail(userId, mailId));
    }

    @PostMapping("/send")
    public ResponseEntity<EmailReturnSendDto> sendEmail(@RequestParam("id") UUID id, @RequestBody @Valid EmailAddDto email){
        return ResponseEntity.status(HttpStatus.CREATED).body(emailService.sendEmail(id, email));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteEmail(@RequestParam("userId") UUID userId,
                                            @RequestParam("mailId") UUID mailId) {
        emailService.hideEmailforUser(userId, mailId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
