package br.com.estacionamento.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Telefone {
    @Id
    private Long id;
    private String numero;

    public Telefone() {
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
