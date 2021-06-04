package br.com.estacionamento.domain;

import javax.persistence.*;

@Entity
public class Telefone {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;

    @ManyToOne(fetch = FetchType.LAZY)
    private Empresa empresa;

    public Telefone() {
    }

    public Telefone(String numero) {
        this.numero = numero;
    }



    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
