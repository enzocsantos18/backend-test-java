package br.com.estacionamento.domain.dto.in.estacionamento;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class EstacionamentoFormUpdateDTO {
    @NotNull @NotEmpty
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
