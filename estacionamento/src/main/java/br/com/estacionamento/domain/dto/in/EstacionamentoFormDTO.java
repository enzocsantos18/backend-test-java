package br.com.estacionamento.domain.dto.in;

import br.com.estacionamento.domain.Empresa;
import br.com.estacionamento.domain.Estacionamento;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class EstacionamentoFormDTO {
    @NotNull @NotEmpty
    private String nome;
    @Min(1)
    private Long id_empresa;


    public EstacionamentoFormDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(Long id_empresa) {
        this.id_empresa = id_empresa;
    }

    public Estacionamento converterParaEstacionamento(Empresa empresa) {
        Estacionamento estacionamento = new Estacionamento();

        estacionamento.setNome(nome);
        estacionamento.setEmpresa(empresa);

        return estacionamento;
    }

}
