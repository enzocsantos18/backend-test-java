package br.com.estacionamento.domain.estacionamento;

import br.com.estacionamento.domain.empresa.Empresa;

import javax.persistence.*;

@Entity
public class Estacionamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @ManyToOne
    private Empresa empresa;

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
