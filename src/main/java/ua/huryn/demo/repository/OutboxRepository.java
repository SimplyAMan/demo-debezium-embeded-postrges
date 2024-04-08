package ua.huryn.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.huryn.demo.entity.Outbox;

import java.sql.Timestamp;

@Repository
public interface OutboxRepository extends JpaRepository<Outbox, Long> {
    @Modifying
    @Query("update Outbox o set o.status = ?1, o.sent = ?2 where o.outboxId = ?3")
    void setStatusById(String status, Timestamp sent, Long outboxId);
}
