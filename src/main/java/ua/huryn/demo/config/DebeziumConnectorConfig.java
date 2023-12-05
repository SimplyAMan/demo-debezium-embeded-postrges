package ua.huryn.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DebeziumConnectorConfig {
    private final DebeziumConnectorProperties properties;

    public DebeziumConnectorConfig(DebeziumConnectorProperties properties) {
        this.properties = properties;
    }

    /**
     * Student database connector.
     *
     * @return Configuration.
     */
    @Bean
    public io.debezium.config.Configuration documentConnector() {
        return io.debezium.config.Configuration.create()
                .with("connector.class", "io.debezium.connector.postgresql.PostgresConnector")
                .with("offset.storage",  "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                .with("offset.storage.file.filename", "c:/temp/offsets.dat")
                .with("offset.flush.interval.ms", 60000)
                .with("name", properties.getSlotName())
                .with("database.server.name", properties.getHostname()+"-"+properties.getDatabase())
                .with("database.hostname", properties.getHostname())
                .with("database.port", properties.getPort())
                .with("database.user", properties.getUsername())
                .with("database.password", properties.getPassword())
                .with("database.dbname", properties.getDatabase())
                .with("topic.prefix",properties.getDatabase()+"."+properties.getTableName())
//                .with("topic.prefix","db.public.document")
                .with("plugin.name","pgoutput")
                .with("table.whitelist", properties.getTableName())
                .with("time.precision.mode","connect")
                .with("slot.name", properties.getSlotName())
                .build();
    }
}
