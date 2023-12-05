package ua.huryn.demo.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class provides the configurations required to setup a Debezium connector
 *
 */
@Configuration
@Getter
public class DebeziumConnectorProperties {
    @Value("${debezium.datasource.hostname}")
    private String hostname;

    @Value("${debezium.datasource.databasename}")
    private String database;

    @Value("${debezium.datasource.port}")
    private String port;

    @Value("${debezium.datasource.username}")
    private String username;

    @Value("${debezium.datasource.password}")
    private String password;

    @Value("${debezium.table_name}")
    private String tableName;

    @Value("${debezium.slot.name:document-postgres-connector}")
    private String slotName;
}
