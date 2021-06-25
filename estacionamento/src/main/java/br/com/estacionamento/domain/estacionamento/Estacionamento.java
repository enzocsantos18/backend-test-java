package br.com.estacionamento.domain.estacionamento;

import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.usuario.Usuario;
import br.com.estacionamento.domain.veiculo.Veiculo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Estacionamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @ManyToOne
    private Empresa empresa;
    @JsonIgnore
    @OneToMany(mappedBy = "estacionamento", cascade = CascadeType.REMOVE)
    private List<Veiculo> veiculos;
    @JsonIgnore
    @OneToMany(mappedBy = "estacionamento", cascade = CascadeType.REMOVE)
    private List<Vaga> vagas;
    @JsonIgnore
    @OneToMany(mappedBy = "estacionamento", cascade = CascadeType.REMOVE)
    private List<Usuario> usuarios;


    public Estacionamento() {
    }



    public String getNome() {
        return nome;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
