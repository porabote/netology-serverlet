package ru.porabote.servlet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import ru.porabote.controller.PostController;
import ru.porabote.repository.PostRepository;
import ru.porabote.service.PostService;

@Configuration
public class AppConfiguration {

    @Bean
    public PostController postController() {
        PostRepository repository = new PostRepository();
        PostService service = new PostService(repository);
        return new PostController(service);
    }

}