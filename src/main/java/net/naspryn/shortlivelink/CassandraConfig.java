package net.naspryn.shortlivelink;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cassandra.core.keyspace.CreateKeyspaceSpecification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableCassandraRepositories(basePackages = {"net.naspryn.shortlivelink.repositories"})
public class CassandraConfig extends AbstractCassandraConfiguration {

    private static final String ENTITIES_BASE_PACKAGE = "net.naspryn.shortlivelink.domain";

    @Value("${cassandra.keyspace}")
    private String keyspace;

    @Value("${cassandra.contact-points}")
    private String contatcPoints;

    @Value("${cassandra.port}")
    private int port;

    @Bean
    public CassandraClusterFactoryBean cluster() {
        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setContactPoints(contatcPoints);
        cluster.setPort(port);
        cluster.setKeyspaceCreations(getKeyspaceCreations());
        return cluster;
    }

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
        return keyspace;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{ENTITIES_BASE_PACKAGE};
    }

    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        List<CreateKeyspaceSpecification> createKeyspaceSpecifications = new ArrayList<>();
        createKeyspaceSpecifications.add(getKeySpaceSpecification());
        return createKeyspaceSpecifications;
    }

    private CreateKeyspaceSpecification getKeySpaceSpecification() {
        CreateKeyspaceSpecification keyspaceSpecification = new CreateKeyspaceSpecification();
        keyspaceSpecification.name(getKeyspaceName());
        keyspaceSpecification.ifNotExists(true).createKeyspace();
        return keyspaceSpecification;
    }
}