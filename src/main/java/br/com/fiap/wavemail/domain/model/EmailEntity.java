package br.com.fiap.wavemail.domain.model;

import br.com.fiap.wavemail.domain.enums.EmailPriority;
import br.com.fiap.wavemail.domain.enums.EmailType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Entity
@Table(name = "TBL_EMAILS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "email_from")
    private String from;
    @Column(name = "email_to")
    private String to;
    @Column(length = 400)
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String body;

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private EmailType type;

    @Enumerated(EnumType.STRING)
    private EmailPriority priority;

    private boolean isRead;
    private boolean isSent;

}
