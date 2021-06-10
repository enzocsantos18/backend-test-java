package br.com.estacionamento.domain.relatorios;


import java.util.Date;

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

    public void setHorario(Date horario) {
        this.horario = horario;
    }

    public Long getEntradas() {
        return entradas;
    }

    public void setEntradas(Long entradas) {
        this.entradas = entradas;
    }

    public Long getSaidas() {
        return saidas;
    }

    public void setSaidas(Long saidas) {
        this.saidas = saidas;
    }
}
