package br.com.estacionamento.domain.dto.in;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class RelatorioFormDTO {
    @NotNull
    @NotEmpty
    @Pattern(regexp = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))")
    private String dt_inicial;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))")
    private String dt_final;
    @Min(1)
    private Long id_estacionamento;

    public String getDt_inicial() {
        return dt_inicial;
    }

    public void setDt_inicial(String dt_inicial) {
        this.dt_inicial = dt_inicial;
    }

    public String getDt_final() {
        return dt_final;
    }

    public void setDt_final(String dt_final) {
        this.dt_final = dt_final;
    }

    public Long getId_estacionamento() {
        return id_estacionamento;
    }

    public void setId_estacionamento(Long id_estacionamento) {
        this.id_estacionamento = id_estacionamento;
    }
}
