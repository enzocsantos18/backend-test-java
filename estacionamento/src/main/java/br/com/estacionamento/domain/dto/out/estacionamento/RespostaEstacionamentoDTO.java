package br.com.estacionamento.domain.dto.out.estacionamento;

import br.com.estacionamento.domain.estacionamento.Estacionamento;
import br.com.estacionamento.domain.estacionamento.Vaga;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RespostaEstacionamentoDTO {

    private Long id;
    private String nome;
    private List<Vaga> vagas;

    public RespostaEstacionamentoDTO(Estacionamento estacionamento) {
        this.id = estacionamento.getId();
        this.nome = estacionamento.getNome();
        this.vagas = estacionamento.getVagas();
    }

    public static List<RespostaEstacionamentoDTO> converter(List<Estacionamento> estacionamentos) {
        return estacionamentos.stream().map(RespostaEstacionamentoDTO::new).collect(Collectors.toList());
    }
}
