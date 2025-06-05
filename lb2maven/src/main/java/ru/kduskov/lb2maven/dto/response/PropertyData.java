package ru.kduskov.lb2maven.dto.response;

import lombok.Data;
import ru.kduskov.lb2maven.enums.PropertyType;
import ru.kduskov.lb2maven.enums.RentalPeriod;

import java.util.List;

@Data
public class PropertyData {
    private Long id;
    private String name;
    private long price;
    private PropertyType type;
    private String address;
    private String description;
    private RentalPeriod period;
    private List<String> photoUrls;
}
