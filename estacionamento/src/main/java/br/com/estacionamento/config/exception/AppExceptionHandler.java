package br.com.estacionamento.config.exception;

import br.com.estacionamento.domain.dto.out.ErroDeFormularioDTO;
import br.com.estacionamento.domain.dto.out.MensagemErroDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;


@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HashMap<String, String> errosMap = new HashMap<>();
        for (final FieldError erro : e.getBindingResult().getFieldErrors()) {
            errosMap.put(erro.getField(), erro.getDefaultMessage());

        }
        for (final ObjectError erro : e.getBindingResult().getGlobalErrors()) {
            errosMap.put(erro.getObjectName(), erro.getDefaultMessage());
        }
        ErroDeFormularioDTO erros = new ErroDeFormularioDTO(HttpStatus.BAD_REQUEST, errosMap);
        return handleExceptionInternal(e, erros, headers, erros.getStatus(), request);

    }

    @ExceptionHandler(value = DomainException.class)
    public ResponseEntity<Object> handleDomainExcpetions(DomainException e, WebRequest request) {
        String mensagem = e.getLocalizedMessage();
        if (mensagem == null) mensagem = e.toString();

        MensagemErroDTO erro = new MensagemErroDTO(LocalDateTime.now(), mensagem);
        return new ResponseEntity<>(erro, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = DomainNotFoundException.class)
    public ResponseEntity<Object> handleDomainNotFoundExcpetions(DomainNotFoundException e, WebRequest request) {
        String mensagem = e.getLocalizedMessage();
        if (mensagem == null) mensagem = e.toString();

        MensagemErroDTO erro = new MensagemErroDTO(LocalDateTime.now(), mensagem);
        return new ResponseEntity<>(erro, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleExeption(Exception e, WebRequest request) {
        String mensagem = e.getLocalizedMessage();
        if (mensagem == null) mensagem = e.toString();

        MensagemErroDTO erro = new MensagemErroDTO(LocalDateTime.now(), mensagem);
        return new ResponseEntity<>(erro, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
