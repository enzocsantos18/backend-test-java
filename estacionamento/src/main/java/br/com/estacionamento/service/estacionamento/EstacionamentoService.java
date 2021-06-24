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

    @Transactional
    public Estacionamento criar(EstacionamentoFormDTO dadosEstacionamento, Long empresaId){
        Optional<Empresa> empresaEncotrada = empresaRepository.findById(empresaId);
        if (!empresaEncotrada.isPresent()){
            throw new DomainNotFoundException("Empresa não encontrada");
        }


        Estacionamento estacionamento = dadosEstacionamento.converterParaEstacionamento(empresaEncotrada.get());
        Optional<Estacionamento> estacionamentoEncontrado = estacionamentoRepository.findByNomeAndEmpresaId(estacionamento.getNome(), empresaId);
        if (estacionamentoEncontrado.isPresent()){
            throw new DomainException("Já existe um estacionamento com esse nome cadastrado!");
        }


        Estacionamento estacionamentoCriado = estacionamentoRepository.save(estacionamento);

        return estacionamentoCriado;
    }

    public List<Estacionamento> listagem(Long empresaId){
        List<Estacionamento> estacionamentos = estacionamentoRepository.findByEmpresaId(empresaId);

        return estacionamentos;
    }

    public Estacionamento buscar(Long empresaId, Long estacionamentoID){
        Optional<Estacionamento> estacionamento = estacionamentoRepository.findByEmpresaIdAndId(empresaId, estacionamentoID);

        if (!estacionamento.isPresent()){
            throw new DomainNotFoundException("Estacionamento não encontrado");
        }

        return estacionamento.get();
    }
    @Transactional
    public void deletar(Long empresaId, Long estacionamentoId) {
        Optional<Estacionamento> estacionamento = estacionamentoRepository.findByEmpresaIdAndId(empresaId, estacionamentoId);

        if (!estacionamento.isPresent()){
            throw new DomainNotFoundException("Estacionamento não encontrado");
        }

        estacionamentoRepository.delete(estacionamento.get());
    }

    @Transactional
    public Estacionamento atualizar(Long empresaId, Long estacionamentoId, EstacionamentoFormUpdateDTO dadosEstacionamento) {
        Optional<Estacionamento> estacionamento = estacionamentoRepository.findByEmpresaIdAndId(empresaId, estacionamentoId);

        if (!estacionamento.isPresent()){
            throw new DomainNotFoundException("Estacionamento não encontrado");
        }

        Optional<Estacionamento> temEstacionamento = estacionamentoRepository.findByNomeAndEmpresaId(dadosEstacionamento.getNome(), empresaId);
        if (temEstacionamento.isPresent()){
            throw new DomainException("Já existe um estacionamento com esse nome cadastrado!");
        }

        Estacionamento estacionamentoEncontrado = estacionamento.get();
        estacionamentoEncontrado.setNome(dadosEstacionamento.getNome());

        Estacionamento estacionamentoAtualizado = estacionamentoRepository.save(estacionamentoEncontrado);

        return estacionamentoAtualizado;
    }
}
