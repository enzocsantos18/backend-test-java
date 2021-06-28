package br.com.estacionamento.domain.dto.out.erro;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MensagemErroDTO {
    private LocalDateTime data;
    private String mensagem;

    public MensagemErroDTO(LocalDateTime data, String mensagem) {
        this.data = data;
        this.mensagem = mensagem;
    }
}
