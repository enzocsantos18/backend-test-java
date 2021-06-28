package br.com.estacionamento.service.estacionamento;

import br.com.estacionamento.config.exception.DomainException;
import br.com.estacionamento.config.exception.DomainNotFoundException;
import br.com.estacionamento.domain.dto.in.estacionamento.EstacionamentoFormDTO;
import br.com.estacionamento.domain.dto.in.estacionamento.EstacionamentoFormUpdateDTO;
import br.com.estacionamento.domain.dto.out.estacionamento.RespostaEstacionamentoDTO;
import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.estacionamento.Estacionamento;
import br.com.estacionamento.domain.usuario.TipoUsuario;
import br.com.estacionamento.domain.usuario.Usuario;
import br.com.estacionamento.repository.empresa.EmpresaRepository;
import br.com.estacionamento.repository.estacionamento.EstacionamentoRepository;
import br.com.estacionamento.repository.usuario.TipoUsuarioRepository;
import br.com.estacionamento.repository.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class EstacionamentoService {

    @Autowired
    private EstacionamentoRepository estacionamentoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<RespostaEstacionamentoDTO> listar(Long empresaId) {
        List<Estacionamento> estacionamentos = estacionamentoRepository.findByEmpresaId(empresaId);
        List<RespostaEstacionamentoDTO> respostaEstacionamentos = RespostaEstacionamentoDTO.converter(estacionamentos);

        return respostaEstacionamentos;
    }

    public RespostaEstacionamentoDTO buscar(Long empresaId, Long estacionamentoId) {
        Estacionamento estacionamento = getEstacionamento(empresaId, estacionamentoId);
        RespostaEstacionamentoDTO respostaEstacionamento = new RespostaEstacionamentoDTO(estacionamento);

        return respostaEstacionamento;
    }

    @Transactional
    public RespostaEstacionamentoDTO criar(EstacionamentoFormDTO dadosEstacionamento, Long empresaId) {
        Empresa empresa = getEmpresa(empresaId);
        Estacionamento estacionamento = dadosEstacionamento.converterParaEstacionamento(empresa);

        verificaDisponibilidadeNome(empresaId, dadosEstacionamento.getNome());

        Estacionamento estacionamentoCriado = estacionamentoRepository.save(estacionamento);
        TipoUsuario tipo = tipoUsuarioRepository.findOneByNome("admin_estacionamento");
        Usuario usuario = dadosEstacionamento.converterParaUsuario(empresa, estacionamentoCriado, tipo);

        verificaDisponibilidadeEmail(dadosEstacionamento.getEmail());

        Usuario usuarioCriado = usuarioRepository.save(usuario);

        RespostaEstacionamentoDTO respostaEstacionamento = new RespostaEstacionamentoDTO(estacionamentoCriado);

        return respostaEstacionamento;
    }

    @Transactional
    public RespostaEstacionamentoDTO atualizar(Long empresaId, Long estacionamentoId, EstacionamentoFormUpdateDTO dadosEstacionamento) {
        Estacionamento estacionamento = getEstacionamento(empresaId, estacionamentoId);
        verificaDisponibilidadeNome(empresaId, dadosEstacionamento.getNome());
        estacionamento.setNome(dadosEstacionamento.getNome());
        Estacionamento estacionamentoAtualizado = estacionamentoRepository.save(estacionamento);
        RespostaEstacionamentoDTO respostaEstacionamento = new RespostaEstacionamentoDTO(estacionamentoAtualizado);

        return respostaEstacionamento;
    }

    @Transactional
    public void deletar(Long empresaId, Long estacionamentoId) {
        Estacionamento estacionamento = getEstacionamento(empresaId, estacionamentoId);
        estacionamentoRepository.delete(estacionamento);
    }


    private void verificaDisponibilidadeEmail(String email) {
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(email);
        if (usuarioEncontrado.isPresent()){
            throw new DomainException("Usuário já cadastrado!");
        }
    }

    private Empresa getEmpresa(Long id) {
        Optional<Empresa> empresa = empresaRepository.findById(id);
        if (!empresa.isPresent()) {
            throw new DomainNotFoundException("Empresa não encontrada");
        }

        return empresa.get();
    }

    private void verificaDisponibilidadeNome(Long empresaId, String nome) {
        Optional<Estacionamento> estacionamento = estacionamentoRepository.findByNomeAndEmpresaId(nome, empresaId);
        if (estacionamento.isPresent()) {
            throw new DomainException("Já existe um estacionamento com esse nome cadastrado!");
        }
    }

    private Estacionamento getEstacionamento(Long empresaId, Long estacionamentoId) {
        Optional<Estacionamento> estacionamento = estacionamentoRepository.findByEmpresaIdAndId(empresaId, estacionamentoId);

        if (!estacionamento.isPresent()) {
            throw new DomainNotFoundException("Estacionamento não encontrado");
        }
        return estacionamento.get();
    }
}
