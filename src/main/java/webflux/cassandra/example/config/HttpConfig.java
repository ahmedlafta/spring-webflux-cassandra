package webflux.cassandra.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.ReactiveCassandraOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import webflux.cassandra.example.event.OnReadyEvent;
import webflux.cassandra.example.http.HackerNewsClient;
import webflux.cassandra.example.repository.ArticleRepository;

@Configuration
public class HttpConfig {

    @Value("${hackernews.url}")
    private String hackernewsUrl;

    @Bean
    public OnReadyEvent initializeDatabase(HackerNewsClient hackerNewsClient, ArticleRepository articleRepository) {
        return new OnReadyEvent(hackerNewsClient, articleRepository);
    }

    @Bean
    public HackerNewsClient hackerNewsClient(WebClient hackerNewsWebClient) {
        return new HackerNewsClient(hackerNewsWebClient);
    }

    @Bean
    public WebClient hackerNewsWebClient() {
        return WebClient.builder()
                .baseUrl(hackernewsUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString())
                .build();
    }
}
