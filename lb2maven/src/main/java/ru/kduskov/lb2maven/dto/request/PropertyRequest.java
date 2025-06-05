package ru.kduskov.lb2maven.dto.request;


import jakarta.validation.constraints.*;

import lombok.Data;
import ru.kduskov.lb2maven.enums.PropertyType;
import ru.kduskov.lb2maven.enums.RentalPeriod;

@Data
public class PropertyRequest {

    @NotBlank(message = "Название обязательно")
    private String name;

    @Min(value = 0, message = "Цена не может быть отрицательной")
    private long price;

    @NotNull(message = "Тип обязателен")
    private PropertyType type;

    @NotBlank(message = "Адрес обязателен")
    private String address;

    @NotBlank(message = "Описание обязательно")
    private String description;

    @NotNull(message = "Период аренды обязателен")
    private RentalPeriod period;
}
