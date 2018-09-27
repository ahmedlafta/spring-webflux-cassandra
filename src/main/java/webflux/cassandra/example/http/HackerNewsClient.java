package webflux.cassandra.example.http;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import webflux.cassandra.example.dto.Article;

public class HackerNewsClient {

    private static final String TOP_STORIES_URI = "/topstories.json";
    private static final String STORY_URI = "/item/%s.json";

    private WebClient webClient;

    public HackerNewsClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<Long> getTopStories() {
        return this.webClient.get()
                .uri(TOP_STORIES_URI)
                .retrieve()
                .bodyToFlux(Long.class);
    }

    public Flux<Article> getArticle(Long id) {
        return this.webClient.get()
                .uri(String.format(STORY_URI, id))
                .retrieve()
                .bodyToFlux(Article.class);
    }
}
