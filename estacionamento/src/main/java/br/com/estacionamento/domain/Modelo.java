package br.com.estacionamento.domain;

import javax.persistence.*;

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

    public String getNome() {
        return nome;
    }

    public TipoVeiculo getTipoVeiculo() {
        return tipoVeiculo;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }
}
