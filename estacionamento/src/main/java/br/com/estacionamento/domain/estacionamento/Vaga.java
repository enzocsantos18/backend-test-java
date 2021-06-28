package br.com.estacionamento.domain.estacionamento;

import br.com.estacionamento.domain.veiculo.TipoVeiculo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Vaga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne
    private Estacionamento estacionamento;
    @ManyToOne
    private TipoVeiculo tipo;
    private Integer quantidade;

    public Vaga() {
    }
}
