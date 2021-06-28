package br.com.estacionamento.domain.estacionamento;

import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.usuario.Usuario;
import br.com.estacionamento.domain.veiculo.Veiculo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
public class Estacionamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String nome;
    @JsonIgnore
    @ManyToOne
    @Getter
    @Setter
    private Empresa empresa;
    @JsonIgnore
    @OneToMany(mappedBy = "estacionamento", cascade = CascadeType.REMOVE)
    private List<Veiculo> veiculos;
    @JsonIgnore
    @OneToMany(mappedBy = "estacionamento", cascade = CascadeType.REMOVE)
    @Getter
    private List<Vaga> vagas;
    @JsonIgnore
    @Getter
    @OneToMany(mappedBy = "estacionamento", cascade = CascadeType.REMOVE)
    private List<Usuario> usuarios;


    public Estacionamento() {
    }

}
