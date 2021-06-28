package br.com.estacionamento.domain.veiculo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Modelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @ManyToOne
    private TipoVeiculo tipoVeiculo;
    @ManyToOne
    private Fabricante fabricante;

}
