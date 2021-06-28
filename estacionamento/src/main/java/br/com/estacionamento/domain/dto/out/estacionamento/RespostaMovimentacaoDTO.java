package br.com.estacionamento.domain.dto.out.estacionamento;

import br.com.estacionamento.domain.estacionamento.Movimentacao;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RespostaMovimentacaoDTO {
    private Long id;
    private String placa;
    private String modelo;
    private String cor;
    private String tipo;
    private LocalDateTime entrada;
    private LocalDateTime saida;

    public RespostaMovimentacaoDTO(Movimentacao movimentacao) {
        this.id = movimentacao.getId();
        this.placa = movimentacao.getVeiculo().getPlaca();
        this.modelo = movimentacao.getVeiculo().getModelo().getNome();
        this.cor = movimentacao.getVeiculo().getCor();
        this.tipo = movimentacao.getVeiculo().getModelo().getTipoVeiculo().getTipo();
        this.entrada = movimentacao.getEntrada();
        this.saida = movimentacao.getSaida();
    }

    public static List<RespostaMovimentacaoDTO> converter(List<Movimentacao> movimentacoes) {
        return movimentacoes.stream().map(RespostaMovimentacaoDTO::new).collect(Collectors.toList());
    }
}
