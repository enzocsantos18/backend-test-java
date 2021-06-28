package br.com.estacionamento.domain.dto.in.estacionamento;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EstacionamentoFormUpdateDTO {
    @NotNull @NotEmpty
    private String nome;
}
