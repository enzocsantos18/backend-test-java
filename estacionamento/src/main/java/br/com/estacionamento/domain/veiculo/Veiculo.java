package br.com.estacionamento.domain.veiculo;

import br.com.estacionamento.domain.estacionamento.Estacionamento;
import br.com.estacionamento.domain.estacionamento.Movimentacao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String placa;
    private String cor;
    @ManyToOne
    private Modelo modelo;
    @JsonIgnore
    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.REMOVE)
    private List<Movimentacao> movimentacoes;
    @ManyToOne
    private Estacionamento estacionamento;

    public Veiculo() {
    }
}
