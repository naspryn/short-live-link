package net.naspryn.shortlivelink;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(basePackages = {"net.naspryn.shortlivelink.repositories"})
public class CassandraConfig extends AbstractCassandraConfiguration {
    private static final String KEYSPACE = "short_live_link";

    @Bean
    public CassandraOperations cassandraOperations() throws Exception {
        return new CassandraTemplate(session().getObject());
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    protected String getKeyspaceName() {
        return KEYSPACE;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{"net.naspryn.shortlivelink.domain"};
    }

}