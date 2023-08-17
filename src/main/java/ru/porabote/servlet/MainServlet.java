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
            this.get(null);
        }
        if (this.httpMethod.equals("GET") && path.matches("/api/posts/\\d+")) {
            // easy way
            final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
            controller.getById(id, resp);
            return;
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
        return;
    }

    private Post getById(Integer id) throws IOException {
        if (id == null) {
            return controller.getById(id, this.resp);
        }
        return null;
    }

    private void add() throws IOException {
        controller.save(this.req.getReader(), resp);
        return;
    }

    private void delete() throws IOException {
        // easy way
        final var id = Long.parseLong(this.path.substring(this.path.lastIndexOf("/")));
        this.controller.removeById(id, this.resp);
    }
}