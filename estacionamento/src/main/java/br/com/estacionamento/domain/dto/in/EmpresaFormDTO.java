package br.com.estacionamento.domain.dto.in;

import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.empresa.Telefone;
import br.com.estacionamento.domain.usuario.TipoUsuario;
import br.com.estacionamento.domain.usuario.Usuario;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.*;

public class EmpresaFormDTO {
    @NotNull @NotEmpty
    private String nome;
    @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}\\-\\d{2}$", message = "O campo cnpj deve seguir o padrão XX.XXX.XXX/XXXX-XX")
    private String cnpj;
    @Pattern(regexp = "^\\([1-9]{2}\\) [0-9]{4,5}-[0-9]{4}$", message = "O campo telefone de seguir o padrão (XX) XXXX-XXXX")
    private String telefone;
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "O campo cep deve seguir o padrão XXXXX-XXX")
    private String cep;
    @NotNull
    @Min(1)
    private Integer numero;
    @Pattern(regexp = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$", message = "email enviado com formato incorreto!")
    @NotBlank
    private String email;
    @NotBlank
    @Length(min = 6, max = 12)
    private String senha;
    @NotBlank
    private String nome_usuario;

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

    public Telefone getTelefone() {
        return new Telefone(this.telefone);
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
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

    public Empresa converterParaEmpresa(){
        Empresa empresa = new Empresa();
        empresa.setNome(this.nome);
        empresa.setCnpj(this.cnpj);

        return empresa;
    }

    public Usuario converterParaUsuario(Empresa empresa, TipoUsuario tipo){
        Usuario usuario = new Usuario();
        usuario.setEmail(this.email);
        usuario.setNome(this.nome_usuario);
        usuario.setSenha( new BCryptPasswordEncoder().encode(this.senha));
        usuario.setEmpresa(empresa);
        usuario.setTipos(tipo);

        return usuario;
    }
}
