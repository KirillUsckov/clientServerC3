package ru.kduskov.lb2maven.dto.response;

import lombok.Data;

@Data
public class PhotoResponse extends BaseResponse{
    private byte[] photo;
}
