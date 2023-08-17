package ru.porabote.servlet;

import ru.porabote.controller.PostController;
import ru.porabote.model.Post;
import ru.porabote.repository.PostRepository;
import ru.porabote.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
    private PostController controller;
    private String path;
    private String httpMethod;
    private HttpServletRequest req;
    private HttpServletResponse resp;

    @Override
    public void init() {
        final var repository = new PostRepository();
        final var service = new PostService(repository);
        controller = new PostController(service);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {

        try {
//            resp.setHeader("Content-type", "application/json");
//            resp.getWriter().print("{\"titles\": \"Hello moto\"}");
            this.httpMethod = req.getMethod();
            this.path = req.getRequestURI();
            this.req = req;
            this.resp = resp;

            this.route();

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void route() throws IOException {
        // primitive routing
        if (this.httpMethod.equals("GET") && path.equals("/api/posts")) {
            this.get();
        }
        if (this.httpMethod.equals("GET") && path.matches("/api/posts/\\d+")) {
            this.getById();
        }
        if (this.httpMethod.equals("POST") && path.equals("/api/posts")) {

        }
        if (this.httpMethod.equals("DELETE") && path.matches("/api/posts/\\d+")) {
            this.delete();
        }
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    private void get() throws IOException {
        controller.all(resp);
    }

    private void getById() throws IOException {
        final var id = Long.parseLong(this.path.substring(path.lastIndexOf("/" + 1)));
        controller.getById(id, this.resp);
    }

    private void add() throws IOException {
        controller.save(this.req.getReader(), resp);
    }

    private void delete() throws IOException {
        final var id = Long.parseLong(this.path.substring(this.path.lastIndexOf("/" + 1)));
        this.controller.removeById(id, this.resp);
    }
}