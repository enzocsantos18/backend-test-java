package br.com.estacionamento.domain.dto.in;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class MovimentacaoFormDTO {

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z]{3}[0-9][A-Za-z0-9][0-9]{2}$", message = "O campo placa deve seguir o padrão ABC1234")
    private String placa;
    @NotNull  @Min(1)
    private Long id_estacionamento;

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Long getId_estacionamento() {
        return id_estacionamento;
    }

    public void setId_estacionamento(Long id_estacionamento) {
        this.id_estacionamento = id_estacionamento;
    }
}
