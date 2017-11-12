package net.naspryn.shortlivelink;

import org.springframework.cassandra.config.CassandraCqlClusterFactoryBean;
import org.springframework.cassandra.config.DataCenterReplication;
import org.springframework.cassandra.core.keyspace.CreateKeyspaceSpecification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableCassandraRepositories(basePackages = {"net.naspryn.shortlivelink"})
public class CassandraConfig extends AbstractCassandraConfiguration {
    private static final String KEYSPACE = "short_live_link";
    private static final String USERNAME = "cassandra";
    private static final String PASSWORD = "cassandra";
    private static final String NODES = "127.0.0.1";


    @Bean
    @Override
    public CassandraCqlClusterFactoryBean cluster() {
        CassandraCqlClusterFactoryBean factoryBean = new CassandraCqlClusterFactoryBean();
        factoryBean.setKeyspaceCreations(getKeyspaceCreations());
        factoryBean.setContactPoints(NODES);
        factoryBean.setUsername(USERNAME);
        factoryBean.setPassword(PASSWORD);
        return factoryBean;
    }

    @Bean
    public CassandraMappingContext cassandraMapping() throws ClassNotFoundException {
        return new BasicCassandraMappingContext();
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
        return KEYSPACE;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{"net.naspryn.shortlivelink"};
    }


    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        List<CreateKeyspaceSpecification> createKeyspaceSpecifications = new ArrayList<>();
        createKeyspaceSpecifications.add(getKeySpaceSpecification());
        return createKeyspaceSpecifications;
    }


    private CreateKeyspaceSpecification getKeySpaceSpecification() {
        DataCenterReplication dcr = new DataCenterReplication("datacenter1", 1L);
        CreateKeyspaceSpecification keyspaceSpecification = new CreateKeyspaceSpecification();
        keyspaceSpecification.name(KEYSPACE);
        keyspaceSpecification.ifNotExists(true).createKeyspace().withNetworkReplication(dcr);
        return keyspaceSpecification;
    }

}