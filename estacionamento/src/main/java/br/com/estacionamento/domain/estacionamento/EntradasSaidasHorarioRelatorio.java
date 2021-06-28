package br.com.estacionamento.domain.estacionamento;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EntradasSaidasHorarioRelatorio {
    private Date horario;
    private Long entradas;
    private Long saidas;

    public EntradasSaidasHorarioRelatorio() {
    }

    public EntradasSaidasHorarioRelatorio(Date horario, Long entradas, Long saidas) {
        this.horario = horario;
        this.entradas = entradas;
        this.saidas = saidas;
    }

    public Date getHorario() {
        this.horario.setSeconds(0);
        this.horario.setMinutes(0);
        return horario;
    }
}
