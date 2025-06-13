package ru.kduskov.lb3maven.dto.response;

import jakarta.validation.constraints.Min;
import lombok.Data;
import ru.kduskov.lb3maven.enums.PropertyType;
import ru.kduskov.lb3maven.enums.RentalPeriod;

import java.util.List;

@Data
public class PropertyData {
    private Long id;
    private String name;
    @Min(0)
    private long price;
    private PropertyType type;
    private String address;
    private String description;
    private RentalPeriod period;
    private List<String> photoUrls;
}
