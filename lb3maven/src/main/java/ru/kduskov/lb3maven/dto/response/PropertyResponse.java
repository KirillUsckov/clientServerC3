package ru.kduskov.lb3maven.dto.response;

import lombok.Data;

@Data
public class PropertyResponse extends BaseResponse {
    private PropertyData data;
}

