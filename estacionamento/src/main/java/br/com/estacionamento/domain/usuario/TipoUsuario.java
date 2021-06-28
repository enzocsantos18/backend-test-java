package br.com.estacionamento.domain.usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class TipoUsuario implements GrantedAuthority {
    @Id
    private Long id;
    private String nome;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return nome;
    }

}
