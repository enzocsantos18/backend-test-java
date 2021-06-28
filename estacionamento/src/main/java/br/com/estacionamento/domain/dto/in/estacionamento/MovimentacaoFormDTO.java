package br.com.estacionamento.domain.dto.in.estacionamento;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class MovimentacaoFormDTO {
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z]{3}[0-9][A-Za-z0-9][0-9]{2}$", message = "O campo placa deve seguir o padrão ABC1234")
    private String placa;
}
