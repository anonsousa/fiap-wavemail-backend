package br.com.fiap.wavemail.domain.service;

import br.com.fiap.wavemail.domain.dto.email.EmailReturnDto;
import br.com.fiap.wavemail.domain.model.EmailEntity;
import br.com.fiap.wavemail.domain.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmailService {
    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private EmailFaker emailFaker;

    @Transactional
    public Page<EmailReturnDto> getReceivedEmails(Pageable pageable) {
        emailFaker.generateRandomEmail();
        Page<EmailEntity> emailEntity = emailRepository.findByIsSentFalse(pageable);

        return emailEntity.map(EmailReturnDto::new);
    }
}
