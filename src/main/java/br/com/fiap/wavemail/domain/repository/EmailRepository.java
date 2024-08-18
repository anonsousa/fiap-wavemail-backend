package br.com.fiap.wavemail.domain.repository;

import br.com.fiap.wavemail.domain.model.EmailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmailRepository extends JpaRepository<EmailEntity, UUID> {

    @Query("SELECT e FROM EmailEntity e WHERE :to MEMBER OF e.to AND e.isSent = false AND :userId NOT MEMBER OF e.hiddenTo")
    Page<EmailEntity> findByRecipientAndIsSentFalseAndUserNotHidden(@Param("to") String to, @Param("userId") UUID userId, Pageable pageable);


}
