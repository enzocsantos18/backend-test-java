package br.com.estacionamento.domain.usuario;


import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.estacionamento.Estacionamento;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Usuario implements UserDetails {
    @Id
    private String email;
    private String senha;
    private String nome;
    @ManyToOne
    private Empresa empresa;
    @ManyToOne
    private Estacionamento estacionamento;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<TipoUsuario> tipos = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return tipos;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Estacionamento getEstacionamento() {
        return estacionamento;
    }

    public void setEstacionamento(Estacionamento estacionamento) {
        this.estacionamento = estacionamento;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
