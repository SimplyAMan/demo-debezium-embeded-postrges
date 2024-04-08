package ua.huryn.demo.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories("ua.huryn.demo.repository")
@ComponentScan(basePackages = { "ua.huryn.demo.*" })
@EntityScan("ua.huryn.demo.entity")
public class DatabaseConfig {

    private final DebeziumConnectorProperties databaseProperties;

    public DatabaseConfig(DebeziumConnectorProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(databaseProperties.getUrl());
        dataSourceBuilder.username(databaseProperties.getUsername());
        dataSourceBuilder.password(databaseProperties.getPassword());
        return dataSourceBuilder.build();
    }
}