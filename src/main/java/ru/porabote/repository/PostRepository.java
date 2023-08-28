package ru.porabote.repository;

import org.springframework.stereotype.Repository;
import ru.porabote.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PostRepository {

  private final static AtomicInteger lastId = new AtomicInteger(0);

  private ConcurrentHashMap<Long, Post> list = new ConcurrentHashMap<>();

  public ConcurrentHashMap<Long, Post> all() {
    return this.list;
  }

  public Post getById(long id) {
    return this.list.get(id);
  }

  public Post save(Post post) {

    Long id = (long) lastId.incrementAndGet();
    this.list.put(id, post);
    return post;
  }

  public boolean removeById(long id) {
    if (this.list.containsKey(id)) {
      this.list.remove(id);
      return true;
    } else {
      return false;
    }
  }
}
