package ua.huryn.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class provides the configurations required to setup a Debezium connector
 *
 */
@Configuration
public class DebeziumConnectorConfig {
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

    private final String TABLE_NAME = "public.document";

    /**
     * Student database connector.
     *
     * @return Configuration.
     */
    @Bean
    public io.debezium.config.Configuration studentConnector() {
        return io.debezium.config.Configuration.create()
                .with("connector.class", "io.debezium.connector.postgresql.PostgresConnector")
                .with("offset.storage",  "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                .with("offset.storage.file.filename", "c:/temp/offsets.dat")
                .with("offset.flush.interval.ms", 60000)
                .with("name", "document-postgres-connector")
                .with("database.server.name", hostname+"-"+ database)
                .with("database.hostname", hostname)
                .with("database.port", port)
                .with("database.user", username)
                .with("database.password", password)
                .with("database.dbname", database)
                .with("topic.prefix","db.public.document")
                .with("plugin.name","pgoutput")
                .with("table.whitelist", TABLE_NAME)
                .with("time.precision.mode","connect")
                .build();
    }
}
