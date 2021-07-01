package br.com.estacionamento.domain.estacionamento;

import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.usuario.Usuario;
import br.com.estacionamento.domain.veiculo.Veiculo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
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
    @OneToMany(mappedBy = "estacionamento", cascade = CascadeType.ALL)
    private List<Veiculo> veiculos;
    @JsonIgnore
    @OneToMany(mappedBy = "estacionamento", cascade = CascadeType.ALL)
    @Getter
    private List<Vaga> vagas;
    @JsonIgnore
    @OneToMany(mappedBy = "estacionamento", cascade = CascadeType.ALL)
    private List<Usuario> usuarios = new ArrayList<>();


    public Estacionamento() {
    }

}
