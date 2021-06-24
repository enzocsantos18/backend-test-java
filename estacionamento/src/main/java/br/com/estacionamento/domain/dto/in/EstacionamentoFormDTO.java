package br.com.estacionamento.domain.dto.in;

import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.estacionamento.Estacionamento;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class EstacionamentoFormDTO {
    @NotNull @NotEmpty
    private String nome;


    public EstacionamentoFormDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public Estacionamento converterParaEstacionamento(Empresa empresa) {
        Estacionamento estacionamento = new Estacionamento();

        estacionamento.setNome(nome);
        estacionamento.setEmpresa(empresa);

        return estacionamento;
    }

}
