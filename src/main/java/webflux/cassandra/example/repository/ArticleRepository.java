package webflux.cassandra.example.repository;

import org.springframework.data.cassandra.core.ReactiveCassandraOperations;
import reactor.core.publisher.Mono;
import webflux.cassandra.example.dto.Article;

/**
 * Repository class for interacting with the Article table - not a traditional spring repository
 */
public class ArticleRepository {

    private ReactiveCassandraOperations reactiveCassandraTemplate;

    public ArticleRepository(ReactiveCassandraOperations reactiveCassandraTemplate) {
        this.reactiveCassandraTemplate = reactiveCassandraTemplate;
    }

    public Mono<Long> countAll() {
        return reactiveCassandraTemplate.count(Article.class);
    }

    public Mono<Article> insert(Article article) {
        return reactiveCassandraTemplate.insert(article);
    }
}
