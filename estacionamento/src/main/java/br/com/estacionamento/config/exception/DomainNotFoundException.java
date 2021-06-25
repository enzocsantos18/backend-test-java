package br.com.estacionamento.config.exception;

public class DomainNotFoundException extends RuntimeException {
    public DomainNotFoundException(String message) {
        super(message);
    }
}
