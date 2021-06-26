package br.com.estacionamento.domain.usuario;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TipoUsuario implements GrantedAuthority {
    @Id
    private Long id;
    private String nome;

    @Override
    public String getAuthority() {
        return nome;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
