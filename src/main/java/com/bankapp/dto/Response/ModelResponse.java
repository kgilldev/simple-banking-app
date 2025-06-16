package com.bankapp.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelResponse {
    private HttpStatus status;
    private boolean success;
    private Object data;
    private Object failure;

    public static ModelResponse success(Object data) {
        return ModelResponse
                .builder()
                .status(HttpStatus.OK)
                .data(data)
                .failure(null)
                .build();
    }

    public static ModelResponse failure(Object failure) {
        return ModelResponse
                .builder()
                .status(HttpStatus.BAD_REQUEST)
                .data(null)
                .failure(failure)
                .build();
    }

}


