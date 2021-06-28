package br.com.estacionamento.domain.usuario;

import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.estacionamento.Estacionamento;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
public class Usuario implements UserDetails {
    @Id
    private String email;
    private String nome;
    private String senha;
    @ManyToOne
    private Empresa empresa;
    @ManyToOne
    private Estacionamento estacionamento;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<TipoUsuario> tipos = new ArrayList<>();

    public void setTipos(TipoUsuario tipo) {
        this.tipos.add(tipo);
    }

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

