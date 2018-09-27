package webflux.cassandra.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraCqlClusterFactoryBean;

import org.springframework.data.cassandra.core.ReactiveCassandraOperations;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import webflux.cassandra.example.repository.ArticleRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class CassandraConfig extends AbstractReactiveCassandraConfiguration {

    @Value("${cassandra.port}")
    private Integer cassandraPort;

    @Value("${cassandra.contact-points}")
    private String cassandraContactPoints;

    @Value("${cassandra.keyspace}")
    private String cassandraKeyspace;

    // TODO refactor sql to file
    private static final String DROP_ARTICLES_TABLE = "" +
            "DROP TABLE IF EXISTS article;";

    private static final String DROP_COMMENTS_TABLE = "" +
            "DROP TABLE IF EXISTS comment;";

    private static final String CREATE_ARTICLES_TABLE = "" +
            "CREATE TABLE IF NOT EXISTS article (" +
            "    id             double    PRIMARY KEY, " +
            "    title          text,    " +
            "    type           text,    " +
            "    url            text,    " +
            "    author         text,    " +
            "    score          double,  " +
            "    descendants    int, " +
            "    kids           list<int>, " +
            "    time           timestamp " +
            ");";

    private static final String CREATE_COMMENTS_TABLE = "" +
            "CREATE TABLE IF NOT EXISTS comment (" +
            "    articleId   double," +
            "    commentId   double," +
            "    PRIMARY KEY (articleId, commentId)" +
            ");";

    @Bean
    @Override
    public CassandraCqlClusterFactoryBean cluster() {
        CassandraCqlClusterFactoryBean bean = new CassandraCqlClusterFactoryBean();
        bean.setKeyspaceCreations(getKeyspaceCreations());
        bean.setContactPoints(cassandraContactPoints);
        bean.setPort(cassandraPort);
        return bean;
    }

    @Override
    protected String getKeyspaceName() {
        return cassandraKeyspace;
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        return Collections.singletonList(CreateKeyspaceSpecification.createKeyspace(cassandraKeyspace)
                .ifNotExists()
                .with(KeyspaceOption.DURABLE_WRITES, true)
                .withSimpleReplication());
    }

    @Override
    protected List<String> getStartupScripts() {
        return Arrays.asList(
                DROP_ARTICLES_TABLE,
                DROP_COMMENTS_TABLE,
                CREATE_ARTICLES_TABLE,
                CREATE_COMMENTS_TABLE
        );
    }

    @Bean
    public ArticleRepository articleRepository(ReactiveCassandraOperations reactiveCassandraOperations) {
        return new ArticleRepository(reactiveCassandraOperations);
    }

}
