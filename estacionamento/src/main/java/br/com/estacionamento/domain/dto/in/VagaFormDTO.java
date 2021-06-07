package br.com.estacionamento.domain.dto.in;


import br.com.estacionamento.domain.Estacionamento;
import br.com.estacionamento.domain.TipoVeiculo;
import br.com.estacionamento.domain.Vaga;

import javax.validation.constraints.Min;





public class VagaFormDTO {
    @Min(1)
    private Long empresa_id;
    @Min(1)
    private Long estacionamento_id;
    @Min(1)
    private Long tipo_id;
    @Min(1)
    private Integer quantidade;

    public Long getEstacionamento_id() {
        return estacionamento_id;
    }

    public void setEstacionamento_id(Long estacionamento_id) {
        this.estacionamento_id = estacionamento_id;
    }

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

    public Long getEmpresa_id() {
        return empresa_id;
    }

    public void setEmpresa_id(Long empresa_id) {
        this.empresa_id = empresa_id;
    }

    public Vaga converterParaVaga(Estacionamento estacionamento, TipoVeiculo tipo){
        Vaga vaga = new Vaga();
        vaga.setEstacionamento(estacionamento);
        vaga.setTipo(tipo);
        vaga.setQuantidade(this.quantidade);

        return vaga;
    }
}
