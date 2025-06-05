package ru.kduskov.lb2maven.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyListResponse extends BaseResponse {
    private List<PropertyData> properties;
}
