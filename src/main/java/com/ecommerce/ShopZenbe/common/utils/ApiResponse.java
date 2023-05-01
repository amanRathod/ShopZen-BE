package com.ecommerce.ShopZenbe.common.utils;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@Data
public class ApiResponse<T> {
    private int statusCode;
    private String message;
    private Map<String, T> data;

    public ApiResponse(int statusCode, String message, T data, String key) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = new HashMap<String, T>();
        this.data.put(key, data);
    }
}

