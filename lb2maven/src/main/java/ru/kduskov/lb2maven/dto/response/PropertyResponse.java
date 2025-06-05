package ru.kduskov.lb2maven.dto.response;

import lombok.Data;
import ru.kduskov.lb2maven.enums.PropertyType;
import ru.kduskov.lb2maven.enums.RentalPeriod;

import java.util.List;

@Data
public class PropertyResponse extends BaseResponse {
    private PropertyData data;
}

