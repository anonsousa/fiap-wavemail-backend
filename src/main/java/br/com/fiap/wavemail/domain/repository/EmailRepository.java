package br.com.fiap.wavemail.domain.repository;

import br.com.fiap.wavemail.domain.model.EmailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmailRepository extends JpaRepository<EmailEntity, UUID> {

    Page<EmailEntity> findByIsSentFalse(Pageable pageable);
}
