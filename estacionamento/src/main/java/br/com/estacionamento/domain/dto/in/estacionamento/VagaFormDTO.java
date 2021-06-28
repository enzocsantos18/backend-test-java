package br.com.estacionamento.domain.dto.in.estacionamento;

import br.com.estacionamento.domain.estacionamento.Estacionamento;
import br.com.estacionamento.domain.estacionamento.Vaga;
import br.com.estacionamento.domain.veiculo.TipoVeiculo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class VagaFormDTO {
    @NotNull  @Min(1)
    private Long tipo_id;
    @NotNull  @Min(1)
    private Integer quantidade;


    public Long getTipo_id() {
        return tipo_id;
    }

    public void setTipo_id(Long tipo_id) {
        this.tipo_id = tipo_id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Vaga converterParaVaga(Estacionamento estacionamento, TipoVeiculo tipo){
        Vaga vaga = new Vaga();
        vaga.setEstacionamento(estacionamento);
        vaga.setTipo(tipo);
        vaga.setQuantidade(this.quantidade);

        return vaga;
    }
}
