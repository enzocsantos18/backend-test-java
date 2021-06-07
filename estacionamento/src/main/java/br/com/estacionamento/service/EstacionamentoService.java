package br.com.estacionamento.service;

import br.com.estacionamento.domain.Empresa;
import br.com.estacionamento.domain.Estacionamento;
import br.com.estacionamento.domain.dto.in.EstacionamentoFormDTO;
import br.com.estacionamento.repository.EmpresaRepository;
import br.com.estacionamento.repository.EstacionamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
