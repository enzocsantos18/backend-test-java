package br.com.estacionamento.domain.dto.in;

import br.com.estacionamento.domain.estacionamento.Estacionamento;
import br.com.estacionamento.domain.usuario.TipoUsuario;
import br.com.estacionamento.domain.usuario.Usuario;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.*;

public class UsuarioFormUpdateDTO {
    @NotNull @NotEmpty
    private String nome;
    @NotBlank
    @Length(min = 6, max = 12)
    private String senha;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return new BCryptPasswordEncoder().encode(this.senha);
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
