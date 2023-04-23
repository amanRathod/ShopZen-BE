package com.ecommerce.ShopZenbe.common.utils;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiResponse<T> {
    private int statusCode;
    private String message;
    private T data;

//    public ApiResponse(int statusCode, String message, T data) {
//        this.statusCode = statusCode;
//        this.message = message;
//        this.data = data;
//    }
}

