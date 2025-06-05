package ru.kduskov.lb3maven.dto.response;

import lombok.Data;

@Data
public class PhotoResponse extends BaseResponse{
    private byte[] photo;
}
