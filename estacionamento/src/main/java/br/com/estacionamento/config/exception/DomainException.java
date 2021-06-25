package br.com.estacionamento.config.exception;

public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}
