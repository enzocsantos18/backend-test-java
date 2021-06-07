package br.com.estacionamento.domain.dto.in;

import br.com.estacionamento.domain.Empresa;
import br.com.estacionamento.domain.Telefone;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
