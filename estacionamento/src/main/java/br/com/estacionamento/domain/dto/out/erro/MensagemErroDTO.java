package br.com.estacionamento.domain.dto.out.erro;

import java.time.LocalDateTime;

public class MensagemErroDTO {
    private LocalDateTime data;
    private String mensagem;


    public MensagemErroDTO() {
    }

    public MensagemErroDTO(LocalDateTime data, String mensagem) {
        this.data = data;
        this.mensagem = mensagem;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
