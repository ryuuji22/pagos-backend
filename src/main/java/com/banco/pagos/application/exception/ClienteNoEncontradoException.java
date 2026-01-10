package com.banco.pagos.application.exception;

/**
 *
 * @author diego
 */
public class ClienteNoEncontradoException extends RuntimeException {

    public ClienteNoEncontradoException(String message) {
        super(message);
    }
}
