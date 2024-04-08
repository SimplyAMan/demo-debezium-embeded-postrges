package ua.huryn.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

import java.util.Date;

@Entity
public class Outbox {
    @Id
    @Column(name = "outbox_id")
    private Long outboxId;
    @Column(name = "payload")
    private String payload;
    @Column(name = "routing_key")
    private String routingKey;
    @Column(name = "status")
    private String status;
    @Column(name = "created")
    private Date created;
    @Column(name = "sent")
    private Date sent;

    @PrePersist
    private void prePersist() {
        if (created == null) {
            created = new Date();
        }
    }
}
