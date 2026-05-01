package com.fredodev.riee.shared.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private static final ZoneId API_ZONE = ZoneId.of("America/La_Paz");

    private boolean success;
    private int status;
    private String message;
    private T data;
    private List<String> errors;
    private OffsetDateTime timestamp = OffsetDateTime.now(API_ZONE);

    public static <T> ApiResponse<T> ok(int status, String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .status(status)
                .message(message)
                .data(data)
                .timestamp(OffsetDateTime.now(API_ZONE))
                .build();
    }

    public static <T> ApiResponse<T> error(int status, String message, List<String> errors) {
        return ApiResponse.<T>builder()
                .success(false)
                .status(status)
                .message(message)
                .errors(errors)
                .timestamp(OffsetDateTime.now(API_ZONE))
                .build();
    }
}
