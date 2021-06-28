package br.com.estacionamento.domain.dto.out.erro;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

public class ErroDeFormularioDTO {

    private HttpStatus status;
    private HashMap<String, String> erros;

    public ErroDeFormularioDTO(HttpStatus status, HashMap<String, String> erros) {
        super();
        this.status = status;
        this.erros = erros;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }


    public HashMap<String, String> getErros() {
        return erros;
    }

    public void setErros(HashMap<String, String> erros) {
        this.erros = erros;
    }
}
