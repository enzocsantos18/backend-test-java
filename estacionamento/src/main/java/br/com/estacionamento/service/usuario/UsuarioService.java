package br.com.estacionamento.service.usuario;

import br.com.estacionamento.config.exception.DomainException;
import br.com.estacionamento.config.exception.DomainNotFoundException;
import br.com.estacionamento.domain.dto.in.UsuarioFormDTO;
import br.com.estacionamento.domain.dto.in.UsuarioFormUpdateDTO;
import br.com.estacionamento.domain.estacionamento.Estacionamento;
import br.com.estacionamento.domain.usuario.TipoUsuario;
import br.com.estacionamento.domain.usuario.Usuario;
import br.com.estacionamento.repository.estacionamento.EstacionamentoRepository;
import br.com.estacionamento.repository.usuario.TipoUsuarioRepository;
import br.com.estacionamento.repository.usuario.UsuarioRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EstacionamentoRepository estacionamentoRepository;
    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;


    public List<Usuario> listar(Usuario usuarioLogado, Long estacionamentoId){
        List<Usuario> lista = new ArrayList<>();
        Optional<Estacionamento> estacionamento = estacionamentoRepository.findById(estacionamentoId);
        if (estacionamento.isEmpty()){
            throw new DomainNotFoundException("Estacionamento não encontrado");
        }

        Estacionamento estacionamentoEncontrado = estacionamento.get();

        if (estacionamentoEncontrado.getEmpresa().getId() != usuarioLogado.getEmpresa().getId()){
            throw new DomainException("Você não tem acesso a essas informações");
        }

        if (usuarioLogado.getEstacionamento() == null || usuarioLogado.getEstacionamento().getId() == estacionamentoId){
            lista = usuarioRepository.findByEstacionamentoId(estacionamentoId);
            return lista;
        }
        else
        {
            throw new DomainException("Você não tem acesso a essas informações");
        }

    }

    @Transactional
    public Usuario criar(UsuarioFormDTO dadosUsuario, Long estacionamentoId) {
        Optional<Estacionamento> estacionamentoEncontrado = estacionamentoRepository.findById(estacionamentoId);

        if (estacionamentoEncontrado.isEmpty()){
            throw new DomainNotFoundException("Estacionamento não encontrado");
        }

        Estacionamento estacionamento = estacionamentoEncontrado.get();

        Optional<TipoUsuario> tipoEncontrado = tipoUsuarioRepository.findById(dadosUsuario.getTipo_usuario_id());

        if (tipoEncontrado.isEmpty()){
            throw new DomainNotFoundException("Tipo de usuário não encontrado");
        }

        TipoUsuario tipoUsuario = tipoEncontrado.get();

        if (tipoUsuario.getNome().equals("admin")){
            throw new DomainException("Não é possível criar usuários desse tipo");
        }

        Usuario usuario = dadosUsuario.converterParaUsuario(estacionamento, tipoUsuario);

        Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(usuario.getEmail());

        if (usuarioEncontrado.isPresent()){
            throw new DomainException("Usuário já cadastrado");
        }


        Usuario usuarioCriado = usuarioRepository.save(usuario);

        return usuarioCriado;

    }

    @Transactional
    public Usuario atualizar(UsuarioFormUpdateDTO dadosUsuario, Usuario usuario) {
        usuario.setSenha(dadosUsuario.getSenha());
        usuario.setNome(dadosUsuario.getNome());

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);

        return usuarioAtualizado;

    }
}
