package ru.kduskov.lb3maven.dto.request;


import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import ru.kduskov.lb3maven.enums.PropertyType;
import ru.kduskov.lb3maven.enums.RentalPeriod;

@Data
@Getter
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
