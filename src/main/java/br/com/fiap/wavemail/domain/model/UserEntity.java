package br.com.fiap.wavemail.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;
    @Column(unique = true, length = 120, nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String password;
    private LocalDateTime createdAt;
    private boolean enabled = true;

    @OneToOne(mappedBy = "user")
    private UserPreferences preferences;

}
