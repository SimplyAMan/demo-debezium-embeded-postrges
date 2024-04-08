package ua.huryn.demo.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
public class OutboxDTO {
    @JsonProperty("outbox_id")
    private Long outboxId;
    @JsonProperty("payload")
    private String payload;
    @JsonProperty("routing_key")
    private String routingKey;
    @JsonProperty("status")
    private String status;
    @JsonProperty("created")
    private Date created;
    @JsonProperty("sent")
    private Date sent;
}
