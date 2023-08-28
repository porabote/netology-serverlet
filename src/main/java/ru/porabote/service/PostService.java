package ru.porabote.service;

import org.springframework.stereotype.Service;
import ru.porabote.exception.NotFoundException;
import ru.porabote.model.Post;
import ru.porabote.repository.PostRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PostService {
  private final PostRepository repository;

  public PostService(PostRepository repository) {
    this.repository = repository;
  }

  public ConcurrentHashMap<Long, Post> all() {
    return repository.all();
  }

  public Post getById(long id) {
    return repository.getById(id);//.orElseThrow(NotFoundException::new);
  }

  public Post save(Post post) {
    return repository.save(post);
  }

  public boolean removeById(long id) {
    return repository.removeById(id);
  }
}

