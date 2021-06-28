package br.com.estacionamento.domain.dto.out.empresa;

import br.com.estacionamento.domain.empresa.Telefone;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespostaTelefoneDTO {
    private Long id;
    private String numero;

    public RespostaTelefoneDTO(Telefone telefone) {
        this.id = telefone.getId();
        this.numero = telefone.getNumero();
    }
}
