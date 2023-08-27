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
    private static final String GET_METHOD = "GET";
    private static final String POST_METHOD = "POST";
    private static final String DELETE_METHOD = "DELETE";
    private static final String API_PREFIX = "api";

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
            this.route(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void route(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String httpMethod = req.getMethod();
        String path = req.getRequestURI();

        String[] segments = path.split("/");
        String modelName = segments[2];

        // primitive routing
        if (httpMethod.equals(GET_METHOD) && path.equals("/" + API_PREFIX + "/" + modelName)) {
            this.get(resp);
        }
        if (httpMethod.equals(GET_METHOD) && path.matches("/" + API_PREFIX + "/" + modelName + "/\\d+")) {
            this.getById(req, resp);
        }
        if (httpMethod.equals(POST_METHOD) && path.equals("/" + API_PREFIX + "/" + modelName)) {

        }
        if (httpMethod.equals(DELETE_METHOD) && path.matches("/" + API_PREFIX + "/" + modelName + "/\\d+")) {
            this.delete(req, resp);
        }
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    private void get(HttpServletResponse resp) throws IOException {
        controller.all(resp);
    }

    private void getById(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String path = req.getRequestURI();

        final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
        controller.getById(id, resp);
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        controller.save(req.getReader(), resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getRequestURI();

        final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
        this.controller.removeById(id, resp);
    }
}