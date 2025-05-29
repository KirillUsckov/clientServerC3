package ru.kduskov.lb1maven.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kduskov.lb1maven.dao.PropertyDao;
import ru.kduskov.lb1maven.model.PropertyPhoto;

import java.io.IOException;

@WebServlet("/api/v1/photo/*")
public class PhotoServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo(); // /{id}
        Long id = Long.parseLong(path.substring(1));

        PropertyPhoto photo = new PropertyDao().findPhotoById(id); // реализуй этот метод

        if (photo != null) {
            resp.setContentType("image/jpeg");
            resp.getOutputStream().write(photo.getData());
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}

