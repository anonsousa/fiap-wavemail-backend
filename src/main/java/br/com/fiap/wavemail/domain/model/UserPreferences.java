package br.com.fiap.wavemail.domain.model;

import br.com.fiap.wavemail.domain.enums.Themes;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "user_preferences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserPreferences implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Themes theme;
    @Column(name = "color_scheme")
    private String colorScheme;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
