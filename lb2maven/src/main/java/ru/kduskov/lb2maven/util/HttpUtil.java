package ru.kduskov.lb2maven.util;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HttpUtil {

    public static HttpHeaders buildImageHeaders(String fileName, long length) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(length);
        headers.setContentDisposition(ContentDisposition.inline().filename(fileName).build());
        return headers;
    }
}
