package ru.kduskov.lb3maven.mapper;

import ru.kduskov.lb3maven.dto.request.PropertyRequest;
import ru.kduskov.lb3maven.dto.response.PropertyData;
import ru.kduskov.lb3maven.model.*;

import java.util.stream.Collectors;

public class PropertyMapper {

    public static Property toEntity(PropertyRequest dto) {
        Property entity = new Property();
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setType(dto.getType());
        entity.setAddress(dto.getAddress());
        entity.setDescription(dto.getDescription());
        entity.setPeriod(dto.getPeriod());
        return entity;
    }

    public static PropertyData toDto(Property entity) {
        PropertyData dto = new PropertyData();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setType(entity.getType());
        dto.setAddress(entity.getAddress());
        dto.setDescription(entity.getDescription());
        dto.setPeriod(entity.getPeriod());
        dto.setPhotoUrls(entity.getPhotos().stream()
                .map(p -> "/api/v1/property/photo/" + p.getId())
                .collect(Collectors.toList()));
        return dto;
    }
}
