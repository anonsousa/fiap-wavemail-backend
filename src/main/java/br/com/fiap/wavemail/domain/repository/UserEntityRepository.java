package br.com.fiap.wavemail.domain.repository;

import br.com.fiap.wavemail.domain.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {

    UserDetails findByEmail(String email);
}
