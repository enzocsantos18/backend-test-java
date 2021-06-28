package br.com.estacionamento.domain.dto.out.erro;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

@Getter
@Setter
public class ErroDeFormularioDTO {

    private HttpStatus status;
    private HashMap<String, String> erros;

    public ErroDeFormularioDTO(HttpStatus status, HashMap<String, String> erros) {
        super();
        this.status = status;
        this.erros = erros;
    }
}
