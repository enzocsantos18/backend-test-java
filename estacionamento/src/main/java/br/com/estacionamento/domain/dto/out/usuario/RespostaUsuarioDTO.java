package br.com.estacionamento.domain.dto.out.usuario;

import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.estacionamento.Estacionamento;
import br.com.estacionamento.domain.estacionamento.Vaga;
import br.com.estacionamento.domain.usuario.TipoUsuario;
import br.com.estacionamento.domain.usuario.Usuario;
import lombok.Getter;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RespostaUsuarioDTO {
    private String nome;
    private String email;
    private List<TipoUsuario> tipos = new ArrayList<>();

    public RespostaUsuarioDTO(Usuario usuario) {
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.tipos = usuario.getTipos();
    }

        public static List<RespostaUsuarioDTO> converter(List<Usuario> usuarios) {
        return usuarios.stream().map(RespostaUsuarioDTO::new).collect(Collectors.toList());
    }
}
