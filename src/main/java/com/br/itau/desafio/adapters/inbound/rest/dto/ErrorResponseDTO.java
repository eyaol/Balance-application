package com.br.itau.desafio.adapters.inbound.rest.dto;

import java.io.Serializable;

public class ErrorResponseDTO implements Serializable {
    private final String timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;

    public ErrorResponseDTO(String timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
