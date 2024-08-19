package br.com.fiap.wavemail.domain.repository;

import br.com.fiap.wavemail.domain.model.CalendarEventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CalendarEventEntityRepository extends JpaRepository<CalendarEventEntity, UUID> {

    @Query("SELECT c FROM CalendarEventEntity c WHERE :email MEMBER OF c.participantsEmails")
    Page<CalendarEventEntity> findByParticipantEmail(@Param("email") String email, Pageable pageable);
}
