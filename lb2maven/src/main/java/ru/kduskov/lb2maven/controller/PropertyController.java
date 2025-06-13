package ru.kduskov.lb2maven.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kduskov.lb2maven.dto.request.PropertyRequest;
import ru.kduskov.lb2maven.dto.response.PropertyData;
import ru.kduskov.lb2maven.dto.response.PropertyListResponse;
import ru.kduskov.lb2maven.dto.response.PropertyResponse;
import ru.kduskov.lb2maven.mapper.PropertyMapper;
import ru.kduskov.lb2maven.model.Property;
import ru.kduskov.lb2maven.model.PropertyPhoto;
import ru.kduskov.lb2maven.service.PropertyService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/property")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @GetMapping
    public ResponseEntity<PropertyListResponse> getAllProperties() {
        List<PropertyData> result = propertyService.findAll().stream().map(PropertyMapper::toDto).collect(Collectors.toList());
        var response = new PropertyListResponse();
        response.setProperties(result);
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PropertyResponse> createProperty(
            @ModelAttribute @Valid PropertyRequest request,
            @RequestPart("photo") MultipartFile photo
    ) {
        Property property = PropertyMapper.toEntity(request);
        propertyService.saveWithPhoto(property, photo);

        PropertyResponse response = new PropertyResponse();
        response.setSuccess(true);
        response.setData(PropertyMapper.toDto(property));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        if (propertyService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        propertyService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
