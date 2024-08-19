package br.com.fiap.wavemail.domain.service;

import br.com.fiap.wavemail.domain.enums.EmailPriority;
import br.com.fiap.wavemail.domain.enums.EmailType;
import br.com.fiap.wavemail.domain.model.EmailEntity;
import br.com.fiap.wavemail.domain.repository.EmailEntityRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class EmailFaker {
    @Autowired
    private EmailEntityRepository emailEntityRepository;

    public static final Faker FAKER = new Faker();

    @Transactional
    public void generateRandomEmail(String email) {
        EmailEntity emailEntity = new EmailEntity();

        emailEntity.setFrom(FAKER.internet().emailAddress());
        emailEntity.setTo(List.of(email));
        emailEntity.setSubject(FAKER.lorem().sentence(10));
        emailEntity.setBody(FAKER.lorem().paragraph(20));
        emailEntity.setDate(LocalDateTime.now().withNano(0));

        emailEntity.setType(EmailType.getRandomType());
        emailEntity.setPriority(EmailPriority.getRandomPriority());

        emailEntity.setRead(false);
        emailEntity.setSent(false);

        emailEntityRepository.save(emailEntity);
    }
}
