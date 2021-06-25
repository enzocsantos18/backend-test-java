package br.com.estacionamento.service.estacionamento;

import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.estacionamento.Estacionamento;
import br.com.estacionamento.domain.dto.in.EstacionamentoFormDTO;
import br.com.estacionamento.domain.dto.in.EstacionamentoFormUpdateDTO;
import br.com.estacionamento.config.exception.DomainException;
import br.com.estacionamento.config.exception.DomainNotFoundException;
import br.com.estacionamento.repository.empresa.EmpresaRepository;
import br.com.estacionamento.repository.estacionamento.EstacionamentoRepository;
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

    public List<Estacionamento> listagem(Long empresaId) {
        List<Estacionamento> estacionamentos = estacionamentoRepository.findByEmpresaId(empresaId);
        return estacionamentos;
    }

    public Estacionamento buscar(Long empresaId, Long estacionamentoId) {
        Estacionamento estacionamento = getEstacionamento(empresaId, estacionamentoId);

        return estacionamento;
    }

    @Transactional
    public Estacionamento criar(EstacionamentoFormDTO dadosEstacionamento, Long empresaId) {
        Optional<Empresa> empresaEncotrada = empresaRepository.findById(empresaId);
        if (!empresaEncotrada.isPresent()) {
            throw new DomainNotFoundException("Empresa não encontrada");
        }

        Estacionamento estacionamento = dadosEstacionamento.converterParaEstacionamento(empresaEncotrada.get());
        verificaDisponibilidadeNome(empresaId, dadosEstacionamento.getNome());

        Estacionamento estacionamentoCriado = estacionamentoRepository.save(estacionamento);

        return estacionamentoCriado;
    }

    @Transactional
    public Estacionamento atualizar(Long empresaId, Long estacionamentoId, EstacionamentoFormUpdateDTO dadosEstacionamento) {
        Estacionamento estacionamento = getEstacionamento(empresaId, estacionamentoId);
        verificaDisponibilidadeNome(empresaId, dadosEstacionamento.getNome());
        estacionamento.setNome(dadosEstacionamento.getNome());
        Estacionamento estacionamentoAtualizado = estacionamentoRepository.save(estacionamento);

        return estacionamentoAtualizado;
    }

    @Transactional
    public void deletar(Long empresaId, Long estacionamentoId) {
        Estacionamento estacionamento = getEstacionamento(empresaId, estacionamentoId);

        estacionamentoRepository.delete(estacionamento);
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
