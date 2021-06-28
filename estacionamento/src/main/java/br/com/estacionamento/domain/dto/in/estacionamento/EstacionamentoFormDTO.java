package br.com.estacionamento.domain.dto.in.estacionamento;

import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.estacionamento.Estacionamento;
import br.com.estacionamento.domain.usuario.TipoUsuario;
import br.com.estacionamento.domain.usuario.Usuario;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class EstacionamentoFormDTO {
    @NotNull @NotEmpty
    private String nome;
    private String email;
    @NotBlank
    @Length(min = 6, max = 12)
    private String senha;
    @NotBlank
    private String nome_usuario;

    public EstacionamentoFormDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome_usuario() {
        return nome_usuario;
    }

    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
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
