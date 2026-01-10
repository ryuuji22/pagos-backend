package com.banco.pagos.application.exception;

public class ProcesoNominaException extends RuntimeException {

    public ProcesoNominaException(String message) {
        super(message);
    }

    public ProcesoNominaException(String message, Throwable cause) {
        super(message, cause);
    }
}
