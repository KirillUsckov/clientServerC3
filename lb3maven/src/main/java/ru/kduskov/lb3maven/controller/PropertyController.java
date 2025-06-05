package ru.kduskov.lb3maven.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kduskov.lb3maven.dto.request.PropertyRequest;
import ru.kduskov.lb3maven.dto.response.PropertyData;
import ru.kduskov.lb3maven.dto.response.PropertyListResponse;
import ru.kduskov.lb3maven.dto.response.PropertyResponse;
import ru.kduskov.lb3maven.mapper.PropertyMapper;
import ru.kduskov.lb3maven.model.Property;
import ru.kduskov.lb3maven.service.PropertyService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/property")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @Operation(
            summary = "Получение списка недвижимости",
            description = "Возвращает список всех объектов недвижимости с их параметрами и фото",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное выполнение"),
                    @ApiResponse(responseCode = "401", description = "Требуется аутентификация"),
                    @ApiResponse(responseCode = "403", description = "Аутентификация предоставлена, но нет доступа"),
                    @ApiResponse(responseCode = "404", description = "Ресурс не найден")
            }
    )
    @GetMapping
    public ResponseEntity<PropertyListResponse> getAllProperties() {
        List<PropertyData> result = propertyService.findAll().stream().map(PropertyMapper::toDto).collect(Collectors.toList());
        var response = new PropertyListResponse();
        response.setProperties(result);
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Создание нового объявления",
            description = "Создаёт новое объявление с описанием недвижимости и фотографией. Используется multipart/form-data.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Объявление успешно создано"),
                    @ApiResponse(responseCode = "400", description = "Неверный формат данных запроса"),
                    @ApiResponse(responseCode = "401", description = "Требуется аутентификация"),
                    @ApiResponse(responseCode = "403", description = "Нет доступа к ресурсу"),
            }
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PropertyResponse> createProperty(
            @RequestPart("data") @Valid PropertyRequest request,
            @RequestPart("photo") MultipartFile photo
    ) {
        Property property = PropertyMapper.toEntity(request);
        propertyService.saveWithPhoto(property, photo);
        var response  = new PropertyResponse();
        response.setData(PropertyMapper.toDto(property));
        response.setSuccess(true);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Удаление объявления",
            description = "Удаляет объект недвижимости по его идентификатору. Поддерживает каскадное удаление фотографий.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Объект успешно удалён"),
                    @ApiResponse(responseCode = "401", description = "Требуется аутентификация"),
                    @ApiResponse(responseCode = "403", description = "Нет доступа к ресурсу"),
                    @ApiResponse(responseCode = "404", description = "Объект с указанным ID не найден")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        if (propertyService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        propertyService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
