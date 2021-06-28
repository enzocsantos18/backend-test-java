package br.com.estacionamento.domain.dto.in.estacionamento;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class RelatorioFormDTO {
    @NotNull
    @NotEmpty
    @Pattern(regexp = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))", message = "O campo deve seguir o padrão AAAA-MM-DD")
    private String dt_inicial;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))", message = "O campo deve seguir o padrão AAAA-MM-DD")
    private String dt_final;

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
}
