package br.com.estacionamento.domain.estacionamento;

import br.com.estacionamento.domain.veiculo.Veiculo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Movimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Veiculo veiculo;
    private LocalDateTime entrada;
    private LocalDateTime saida;

    public Movimentacao() {
    }
}
