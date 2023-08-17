package ru.porabote.repository;

import ru.porabote.model.Post;

import java.util.*;

// Stub
public class PostRepository {

  private HashMap<Long, Post> list = new HashMap<>();

  public HashMap<Long, Post> all() {
    return this.list;
  }

  public Post getById(long id) {
    return this.list.get(id);
  }

  public Post save(Post post) {
    this.list.put(post.getId(), post);
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
