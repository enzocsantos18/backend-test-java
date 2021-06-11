package br.com.estacionamento.domain.exception;

public class DomainNotFoundException extends RuntimeException{
    public DomainNotFoundException(String message) {
        super(message);
    }
}
