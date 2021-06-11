package br.com.estacionamento.domain.exception;

public class DomainException extends RuntimeException{
    public DomainException(String message) {
        super(message);
    }
}
