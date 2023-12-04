package ua.huryn.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
//@AllArgsConstructor
public class Result {
    @Id
    @Column(name="result_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("document_id")
    private Long documentId;
    @Column(name="created_origin")
    private Date created;
    @Column(name="created")
    private Date createdNew;

    public Result(Long documentId, Date created) {
        this.documentId = documentId;
        this.created = created;
    }

    @PrePersist
    void preInsert() {
        if (this.createdNew == null)
            this.createdNew = new Date();
    }
}
