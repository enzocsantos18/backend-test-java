package br.com.estacionamento.domain.estacionamento;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntradasSaidasRelatorio {
    private Long entradas;
    private Long saidas;

    public EntradasSaidasRelatorio(Long entradas, Long saidas) {
        this.entradas = entradas;
        this.saidas = saidas;
    }
}
