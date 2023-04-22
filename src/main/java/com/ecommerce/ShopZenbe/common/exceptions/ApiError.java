package com.ecommerce.ShopZenbe.common.exceptions;

import java.time.LocalDateTime;
import java.util.List;

public record ApiError(
        String path,
        String error,
        Object message,
        int statusCode,
        LocalDateTime localDateTime
) {
    public static ApiError fromSingleMessage(String path, String error, String message, int statusCode, LocalDateTime localDateTime) {
        return new ApiError(path, message, error, statusCode, localDateTime);
    }

    public static ApiError fromListMessage(String path, String error, List<String> message, int statusCode, LocalDateTime localDateTime) {
        return new ApiError(path, error, message, statusCode, localDateTime);
    }
}