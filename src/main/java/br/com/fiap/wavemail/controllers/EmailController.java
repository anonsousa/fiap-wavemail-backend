package br.com.fiap.wavemail.controllers;

import br.com.fiap.wavemail.domain.dto.email.EmailReturnDto;
import br.com.fiap.wavemail.domain.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @GetMapping("/received")
    public ResponseEntity<Page<EmailReturnDto>> getAllReceivedEmails(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(emailService.getReceivedEmails(pageable));
    }
}
