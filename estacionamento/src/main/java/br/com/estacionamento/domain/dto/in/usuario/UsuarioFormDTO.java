package br.com.estacionamento.domain.dto.in.usuario;

import br.com.estacionamento.domain.estacionamento.Estacionamento;
import br.com.estacionamento.domain.usuario.TipoUsuario;
import br.com.estacionamento.domain.usuario.Usuario;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.*;

@Getter
@Setter
public class UsuarioFormDTO {
    @NotNull
    @NotEmpty
    private String nome;
    @Pattern(regexp = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$", message = "email enviado com formato incorreto!")
    @NotBlank
    private String email;
    @NotBlank
    @Length(min = 6, max = 12)
    private String senha;
    @NotNull
    @Min(1L)
    private Long tipo_usuario_id;

    public Usuario converterParaUsuario(Estacionamento estacionamento, TipoUsuario tipo) {
        Usuario usuario = new Usuario();
        usuario.setEmail(this.email);
        usuario.setNome(this.nome);
        usuario.setSenha(new BCryptPasswordEncoder().encode(this.senha));
        usuario.setEmpresa(estacionamento.getEmpresa());
        usuario.setEstacionamento(estacionamento);
        usuario.setTipos(tipo);

        return usuario;
    }
}
