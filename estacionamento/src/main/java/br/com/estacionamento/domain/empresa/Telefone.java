package br.com.estacionamento.domain.empresa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Telefone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String numero;
    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    private Empresa empresa;

    public Telefone() {
    }

    public Telefone(String numero) {
        this.numero = numero;
    }
}
