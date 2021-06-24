package br.com.estacionamento.domain.estacionamento;

public class EntradasSaidasRelatorio {
    private Long entradas;
    private Long saidas;


    public EntradasSaidasRelatorio(Long entradas, Long saidas) {
        this.entradas = entradas;
        this.saidas = saidas;
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
