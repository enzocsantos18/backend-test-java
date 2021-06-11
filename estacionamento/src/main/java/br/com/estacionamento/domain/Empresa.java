package br.com.estacionamento.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Empresa {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cnpj;
    @OneToMany (mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<Telefone> telefone = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;

    public Empresa() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public List<Telefone> getTelefone() {
        return telefone;
    }

    public void setTelefone(List<Telefone> telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public void adicionarTelefone(Telefone telefone){
        this.telefone.add(telefone);
    }
}
