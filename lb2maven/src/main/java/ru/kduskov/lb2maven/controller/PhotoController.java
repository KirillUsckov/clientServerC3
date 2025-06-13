package ru.kduskov.lb2maven.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kduskov.lb2maven.model.PropertyPhoto;
import ru.kduskov.lb2maven.service.PropertyService;
import ru.kduskov.lb2maven.util.HttpUtil;

@RestController
@RequestMapping("/api/v1/property/photo")
@RequiredArgsConstructor
public class PhotoController {

    private final PropertyService propertyService;

    @Operation(
            summary = "Получение фотографии по ID",
            description = "Возвращает изображение, связанное с объектом недвижимости по его идентификатору. Ответ содержит бинарные данные изображения с корректными заголовками.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Фотография успешно получена (image/jpeg)"),
                    @ApiResponse(responseCode = "404", description = "Фотография не найдена или отсутствуют данные"),
                    @ApiResponse(responseCode = "401", description = "Требуется аутентификация"),
                    @ApiResponse(responseCode = "403", description = "Нет доступа к ресурсу")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable Long id) {
        PropertyPhoto photo = propertyService.findPhotoById(id);
        if (photo == null || photo.getData() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        HttpHeaders headers = HttpUtil.buildImageHeaders(photo.getFileName(), photo.getData().length);
        return new ResponseEntity<>(photo.getData(), headers, HttpStatus.OK);
    }
}
