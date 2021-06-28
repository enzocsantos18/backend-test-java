package br.com.estacionamento.domain.dto.out.estacionamento;

import br.com.estacionamento.domain.estacionamento.Vaga;
import br.com.estacionamento.domain.veiculo.TipoVeiculo;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RespostaVagaDTO {
    private Long id;
    private TipoVeiculo tipo;
    private Integer quantidade;

    public RespostaVagaDTO(Vaga vaga) {
        this.id = vaga.getId();
        this.tipo = vaga.getTipo();
        this.quantidade = vaga.getQuantidade();
    }

    public static List<RespostaVagaDTO> converter(List<Vaga> vagas) {
        return vagas.stream().map(RespostaVagaDTO::new).collect(Collectors.toList());
    }
}
