package ua.huryn.demo.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ua.huryn.demo.repository.OutboxRepository;

import java.sql.Timestamp;

@Service
public class OutboxService {
    private final OutboxRepository repository;

    public OutboxService(OutboxRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void setStatusById(String status, Timestamp sent, Long outboxId){
        repository.setStatusById(status, sent, outboxId);
    }
}
