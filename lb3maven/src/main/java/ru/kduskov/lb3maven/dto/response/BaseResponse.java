package ru.kduskov.lb3maven.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {
    private boolean success;
    private String details;

    public static BaseResponse ok(String message) {
        return new BaseResponse(true, message);
    }

    public static BaseResponse fail(String message) {
        return new BaseResponse(false, message);
    }
}
