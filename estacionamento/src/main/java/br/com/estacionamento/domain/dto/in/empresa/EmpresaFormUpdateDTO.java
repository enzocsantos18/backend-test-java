package br.com.estacionamento.domain.dto.in.empresa;

import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.empresa.Telefone;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class EmpresaFormUpdateDTO {
    @NotNull @NotEmpty
    private String nome;
    @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}\\-\\d{2}$", message = "O campo cnpj deve seguir o padrão XX.XXX.XXX/XXXX-XX")
    private String cnpj;
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "O campo cep deve seguir o padrão XXXXX-XXX")
    private String cep;
    @NotNull  @Min(1)
    private Integer numero;


    public String getCep() {
        return cep.replace("-", "");
    }

    public Empresa converterParaEmpresa(Empresa empresa){
        empresa.setNome(this.nome);
        empresa.setCnpj(this.cnpj);

        return empresa;
    }
}
