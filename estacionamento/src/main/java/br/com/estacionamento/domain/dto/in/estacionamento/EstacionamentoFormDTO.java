package br.com.estacionamento.domain.dto.in.estacionamento;

import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.estacionamento.Estacionamento;
import br.com.estacionamento.domain.usuario.TipoUsuario;
import br.com.estacionamento.domain.usuario.Usuario;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class EstacionamentoFormDTO {
    @NotNull @NotEmpty
    private String nome;
    @Pattern(regexp = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$", message = "email enviado com formato incorreto!")
    @NotBlank
    private String email;
    @NotBlank
    @Length(min = 6, max = 12)
    private String senha;
    @NotBlank
    private String nome_usuario;

    public EstacionamentoFormDTO() {
    }

    public Estacionamento converterParaEstacionamento(Empresa empresa) {
        Estacionamento estacionamento = new Estacionamento();

        estacionamento.setNome(nome);
        estacionamento.setEmpresa(empresa);

        return estacionamento;
    }

    public Usuario converterParaUsuario(Empresa empresa,Estacionamento estacionamento, TipoUsuario tipo){
        Usuario usuario = new Usuario();
        usuario.setEmail(this.email);
        usuario.setNome(this.nome_usuario);
        usuario.setSenha( new BCryptPasswordEncoder().encode(this.senha));
        usuario.setEmpresa(empresa);
        usuario.setEstacionamento(estacionamento);
        usuario.setTipos(tipo);
        return usuario;
    }

}
