package com.prueba.cliente.service.response;

import com.prueba.cliente.dto.ApiResponseClient;
import com.prueba.cliente.dto.ResponseProcess;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ResponseBuilderCustom {

    public <T> ApiResponseClient<T> buildSuccess(T data, HttpStatus status, String message) {
        ResponseProcess responseProcess = new ResponseProcess(
                String.valueOf(status.value()),
                message,
                "OK"
        );
        return new ApiResponseClient<>(data, responseProcess);
    }

    public <T> ApiResponseClient<T> buildError(HttpStatus status, String message, String details) {
        ResponseProcess responseProcess = new ResponseProcess(
                String.valueOf(status.value()),
                message,
                details
        );
        return new ApiResponseClient<>(null, responseProcess);
    }
}
