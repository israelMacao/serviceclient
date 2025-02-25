package com.prueba.cliente.exception;

public class BankBusinessException extends RuntimeException{
    public BankBusinessException(String message) {
        super(message);
    }
}

