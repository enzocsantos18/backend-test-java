package br.com.estacionamento.domain.dto.in.usuario;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UsuarioFormUpdateDTO {
    @NotNull
    @NotEmpty
    private String nome;
    @NotBlank
    @Length(min = 6, max = 12)
    private String senha;

    public String getSenha() {
        return new BCryptPasswordEncoder().encode(this.senha);
    }
}
