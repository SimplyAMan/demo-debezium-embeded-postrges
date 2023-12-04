package ua.huryn.demo.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.debezium.config.Configuration;
import io.debezium.embedded.EmbeddedEngine;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.tuple.Pair;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import ua.huryn.demo.entity.Result;
import ua.huryn.demo.repository.ResultRepository;
import ua.huryn.demo.utils.Operation;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static io.debezium.data.Envelope.FieldName.*;
import static java.util.stream.Collectors.toMap;

@Slf4j
@Component
public class CDCListener {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private EmbeddedEngine engine;

    private final ObjectMapper objectMapper;
    private final ResultRepository repository;

    private CDCListener(Configuration connector, ObjectMapper objectMapper, ResultRepository repository) {
        this.engine = EmbeddedEngine
                .create()
                .using(connector)
                .notifying(this::handleEvent)
                .build();
        this.objectMapper = objectMapper;
        this.repository = repository;
    }

    /**
     * This method is invoked when a transactional action is performed on any of the tables that were configured.
     *
     * @param sourceRecord
     */
    private void handleEvent(SourceRecord sourceRecord) {
        Struct sourceRecordValue = (Struct) sourceRecord.value();

        log.info("sourceRecord: {}", sourceRecord);
        log.info("sourceRecordValue: {}", sourceRecordValue);

        if(sourceRecordValue != null) {
            Operation operation = Operation.forCode((String) sourceRecordValue.get(OPERATION));

            //Only if this is a transactional operation.
            if(operation != Operation.READ) {

                Map<String, Object> message;
                String record = AFTER; //For Update & Insert operations.

                if (operation == Operation.DELETE) {
                    record = BEFORE; //For Delete operations.
                }
                //Build a map with all row data received.
                Struct struct = (Struct) sourceRecordValue.get(record);
                message = struct.schema().fields().stream()
                        .map(Field::name)
                        .filter(fieldName -> struct.get(fieldName) != null)
                        .map(fieldName -> Pair.of(fieldName, struct.get(fieldName)))
                        .collect(toMap(Pair::getKey, Pair::getValue));
                log.info("Data Changed: {} with Operation: {}", message, operation.name());

                objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
                Result result = objectMapper.convertValue(message, Result.class);
                log.info("result: {}", result);
                repository.save(result);
            }
        }
    }

    /**
     * The method is called after the Debezium engine is initialized and started asynchronously using the Executor.
     */
    @PostConstruct
    private void start() {
        this.executor.execute(engine);
    }

    /**
     * This method is called when the container is being destroyed. This stops the debezium, merging the Executor.
     */
    @PreDestroy
    private void stop() {
        if (this.engine != null) {
            this.engine.stop();
        }
    }
}
