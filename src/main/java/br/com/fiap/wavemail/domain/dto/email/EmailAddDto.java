package br.com.fiap.wavemail.domain.dto.email;

public record EmailAddDto(

    String from,
    String to,
    String subject,
    String body

) { }
