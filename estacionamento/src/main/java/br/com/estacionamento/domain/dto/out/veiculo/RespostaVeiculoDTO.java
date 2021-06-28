package br.com.estacionamento.domain.dto.out.veiculo;

import br.com.estacionamento.domain.estacionamento.Estacionamento;
import br.com.estacionamento.domain.estacionamento.Movimentacao;
import br.com.estacionamento.domain.estacionamento.Vaga;
import br.com.estacionamento.domain.veiculo.Modelo;
import br.com.estacionamento.domain.veiculo.TipoVeiculo;
import br.com.estacionamento.domain.veiculo.Veiculo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RespostaVeiculoDTO {
    private Long id;
    private String placa;
    private String cor;
    private Modelo modelo;
    private Estacionamento estacionamento;


    public RespostaVeiculoDTO(Veiculo veiculo) {
        this.id = veiculo.getId();
        this.placa = veiculo.getPlaca();
        this.cor = veiculo.getCor();
        this.modelo = veiculo.getModelo();
        this.estacionamento = veiculo.getEstacionamento();
    }

    public static List<RespostaVeiculoDTO> converter(List<Veiculo> veiculos) {
        return veiculos.stream().map(RespostaVeiculoDTO::new).collect(Collectors.toList());
    }
}
