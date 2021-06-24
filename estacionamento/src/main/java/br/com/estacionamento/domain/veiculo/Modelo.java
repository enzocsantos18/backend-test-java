package br.com.estacionamento.domain.veiculo;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipoVeiculo(TipoVeiculo tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }
}
