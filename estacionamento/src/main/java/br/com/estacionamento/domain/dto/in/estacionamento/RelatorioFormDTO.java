package br.com.estacionamento.domain.dto.in.estacionamento;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class RelatorioFormDTO {
    @NotNull
    @NotEmpty
    @Pattern(regexp = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))", message = "O campo deve seguir o padrão AAAA-MM-DD")
    private String dt_inicial;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))", message = "O campo deve seguir o padrão AAAA-MM-DD")
    private String dt_final;
}
