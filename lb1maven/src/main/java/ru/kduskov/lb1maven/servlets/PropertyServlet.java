package ru.kduskov.lb1maven.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;
import ru.kduskov.lb1maven.model.Property;
import ru.kduskov.lb1maven.dao.PropertyDao;
import ru.kduskov.lb1maven.model.PropertyPhoto;
import ru.kduskov.lb1maven.enums.PropertyType;
import ru.kduskov.lb1maven.enums.RentalPeriod;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@WebServlet("/api/v1/property")
@MultipartConfig
@Slf4j
public class PropertyServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PropertyDao propertyDao = new PropertyDao();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");

        Property property = validateAndBuildProperty(req, resp);
        if (property == null) return;

        for (Part part : req.getParts()) {
            if ("photos".equals(part.getName()) && part.getSize() > 0) {
                PropertyPhoto photo = new PropertyPhoto();
                photo.setFileName(part.getSubmittedFileName());
                photo.setData(part.getInputStream().readAllBytes());
                photo.setProperty(property);
                property.getPhotos().add(photo);
            }
        }

        propertyDao.save(property);

        resp.setContentType("application/json");
        resp.getWriter().write("{\"success\":true}");
    }

    private Property validateAndBuildProperty(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String priceStr = req.getParameter("price");
        String typeStr = req.getParameter("type");
        String address = req.getParameter("address");
        String periodStr = req.getParameter("period");
        String description = req.getParameter("description");

        if (Stream.of(name, priceStr, typeStr, address, periodStr, description).anyMatch(s -> s == null || s.isBlank())) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"success\":false,\"error\":\"Все поля обязательны\"}");
            return null;
        }

        Property property = new Property();
        property.setName(name);
        property.setPrice(Long.parseLong(priceStr));
        property.setType(PropertyType.valueOf(typeStr));
        property.setAddress(address);
        property.setPeriod(RentalPeriod.valueOf(periodStr));
        property.setDescription(description);
        return property;
    }


    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Map<String, Object> responseJson = new HashMap<>();
        try {
            String idParam = req.getParameter("id");
            if (idParam == null) {
                throw new IllegalArgumentException("В запросе не передан id");
            }
            Long id = Long.parseLong(idParam);
            propertyDao.deleteById(id);
            responseJson.put("success", true);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (IllegalArgumentException e){
            responseJson.put("success", false);
            responseJson.put("error", e.getMessage());
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        objectMapper.writeValue(resp.getWriter(), responseJson);

    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        List<Property> properties = propertyDao.findAll();

        // Принудительная инициализация коллекции photos
        for (Property property : properties) {
            property.getPhotos().size();
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(resp.getOutputStream(), properties);
    }
}
