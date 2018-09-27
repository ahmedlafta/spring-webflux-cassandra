package webflux.cassandra.example.event;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import webflux.cassandra.example.http.HackerNewsClient;
import webflux.cassandra.example.repository.ArticleRepository;

/**
 * Event listener which will initialize the database with data once the application is ready
 */
public class OnReadyEvent {

    private HackerNewsClient hackerNewsClient;
    private ArticleRepository articleRepository;

    public OnReadyEvent(HackerNewsClient hackerNewsClient, ArticleRepository articleRepository) {
        this.hackerNewsClient = hackerNewsClient;
        this.articleRepository = articleRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        this.hackerNewsClient
                .getTopStories()
                .flatMap(hackerNewsClient::getArticle)
                .flatMap(articleRepository::insert)
                .then(articleRepository.countAll())
                .log()
                .subscribe();
    }
}
