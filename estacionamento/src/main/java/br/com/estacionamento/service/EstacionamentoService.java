package br.com.estacionamento.service;

import br.com.estacionamento.domain.Empresa;
import br.com.estacionamento.domain.Estacionamento;
import br.com.estacionamento.domain.dto.in.EstacionamentoFormDTO;
import br.com.estacionamento.repository.EmpresaRepository;
import br.com.estacionamento.repository.EstacionamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;


@Service
public class EstacionamentoService {

    @Autowired
    private EstacionamentoRepository estacionamentoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;


    public Estacionamento criar(EstacionamentoFormDTO dadosEstacionamento){
        Optional<Empresa> empresaEncotrada = empresaRepository.findById(dadosEstacionamento.getId_empresa());
        if (!empresaEncotrada.isPresent()){
            throw new RuntimeException("Empresa não encontrada");
        }

        Estacionamento estacionamento = dadosEstacionamento.converterParaEstacionamento(empresaEncotrada.get());

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
            throw new RuntimeException("Estacionamento não existe");
        }

        return estacionamento.get();
    }

    public void deletar(Long empresaId, Long estacionamentoId) {
        Optional<Estacionamento> estacionamento = estacionamentoRepository.findByEmpresaIdAndId(empresaId, estacionamentoId);

        if (!estacionamento.isPresent()){
            throw new RuntimeException("Estacionamento não existe");
        }

        estacionamentoRepository.delete(estacionamento.get());
    }
}
