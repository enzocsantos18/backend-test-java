package br.com.estacionamento.domain.dto.in.empresa;

import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.empresa.Telefone;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class EmpresaFormUpdateDTO {
    @NotNull @NotEmpty
    private String nome;
    @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}\\-\\d{2}$", message = "O campo cnpj deve seguir o padrão XX.XXX.XXX/XXXX-XX")
    private String cnpj;
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "O campo cep deve seguir o padrão XXXXX-XXX")
    private String cep;
    @NotNull  @Min(1)
    private Integer numero;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }


    public Empresa converterParaEmpresa(Empresa empresa){
        empresa.setNome(this.nome);
        empresa.setCnpj(this.cnpj);

        return empresa;
    }
}
