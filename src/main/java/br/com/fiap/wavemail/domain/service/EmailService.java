package br.com.fiap.wavemail.domain.service;

import br.com.fiap.wavemail.domain.dto.email.EmailAddDto;
import br.com.fiap.wavemail.domain.dto.email.EmailReturnReceivedDto;
import br.com.fiap.wavemail.domain.dto.email.EmailReturnSendDto;
import br.com.fiap.wavemail.domain.model.EmailEntity;
import br.com.fiap.wavemail.domain.model.UserEntity;
import br.com.fiap.wavemail.domain.repository.EmailEntityRepository;
import br.com.fiap.wavemail.domain.repository.UserEntityRepository;
import br.com.fiap.wavemail.infra.exceptions.ItemNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class EmailService {
    @Autowired
    private EmailEntityRepository emailEntityRepository;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private EmailFaker emailFaker;

    @Transactional
    public EmailReturnSendDto sendEmail(UUID userId, EmailAddDto email){
        UserEntity userEntity = userEntityRepository.findById(userId).orElseThrow(() -> new ItemNotFoundException("User not found!"));
        EmailEntity emailEntity = new EmailEntity();

        List<String> uniqueRecipients = new ArrayList<>(new LinkedHashSet<>(email.to()));
        List<String> ccRecipients = new ArrayList<>(new LinkedHashSet<>(email.cc()));

        BeanUtils.copyProperties(email, emailEntity);
        emailEntity.setFrom(userEntity.getEmail());
        emailEntity.setTo(uniqueRecipients);
        emailEntity.setCc(ccRecipients);
        emailEntity.setDate(LocalDateTime.now().withNano(0));
        emailEntity.setSent(true);

        return new EmailReturnSendDto(emailEntityRepository.save(emailEntity));
    }

    @Transactional
    public Page<EmailReturnReceivedDto> getReceivedEmails(UUID id, Pageable pageable) {
        UserEntity userEntity = userEntityRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("User not found!"));
        String userEmail = userEntity.getEmail();

        emailFaker.generateRandomEmail(userEmail);
        Page<EmailEntity> emailEntity = emailEntityRepository.findByRecipientAndIsSentFalseAndUserNotHidden(userEmail, userEntity.getId(), pageable);

        return emailEntity.map(EmailReturnReceivedDto::new);
    }

    @Transactional(readOnly = true)
    public EmailReturnReceivedDto getReceivedEmail(UUID userId, UUID mailId) {
        EmailEntity emailEntity = emailEntityRepository.findById(mailId)
                .orElseThrow(() -> new ItemNotFoundException("Email not found!"));

        UserEntity userEntity = userEntityRepository.findById(userId)
                .orElseThrow(() -> new ItemNotFoundException("User not found!"));

        if (emailEntity.getTo().contains(userEntity.getEmail())){
            return new EmailReturnReceivedDto(emailEntity);
        } else {
            throw new ItemNotFoundException("User not allowed to this email!");
        }
    }

    @Transactional
    public void hideEmailforUser(UUID userId, UUID mailId) {
        UserEntity userEntity = userEntityRepository.findById(userId)
                .orElseThrow(() -> new ItemNotFoundException("User not found!"));

        EmailEntity emailEntity = emailEntityRepository.findById(mailId).orElseThrow(() -> new ItemNotFoundException("Email not found!"));

        List<UUID> hiddenToList = emailEntity.getHiddenTo();

        if (hiddenToList == null){
            hiddenToList = new ArrayList<>();
            emailEntity.setHiddenTo(hiddenToList);
        }

        if (!hiddenToList.contains(userId)){
            hiddenToList.add(userId);
        }

        emailEntityRepository.save(emailEntity);

    }

}



