package br.com.estacionamento.domain.dto.out.usuario;

import br.com.estacionamento.domain.usuario.TipoUsuario;
import br.com.estacionamento.domain.usuario.Usuario;
import lombok.Getter;

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
