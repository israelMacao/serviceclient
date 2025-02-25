package com.prueba.cliente.dto;

import lombok.Data;

@Data
public class ApiResponseClient<T> {
    private T details;
    private ResponseProcess responseProcess;

    public ApiResponseClient(T details, ResponseProcess responseProcess) {
        this.details = details;
        this.responseProcess = responseProcess;
    }
}