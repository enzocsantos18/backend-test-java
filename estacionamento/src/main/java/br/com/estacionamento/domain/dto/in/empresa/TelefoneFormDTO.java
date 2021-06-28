package br.com.estacionamento.domain.dto.in.empresa;

import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.empresa.Telefone;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class TelefoneFormDTO {
    @Pattern(regexp = "^\\([1-9]{2}\\) [0-9]{4,5}-[0-9]{4}$", message = "O campo telefone de seguir o padrão (XX) XXXX-XXXX")
    private String numero;

    public Telefone converterParaTelefone(Empresa empresa){
        Telefone telefone = new Telefone();
        telefone.setEmpresa(empresa);
        telefone.setNumero(this.numero);

        return telefone;
    }
}
