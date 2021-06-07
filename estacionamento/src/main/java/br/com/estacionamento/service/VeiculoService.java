package br.com.estacionamento.service;

import br.com.estacionamento.domain.Estacionamento;
import br.com.estacionamento.domain.Modelo;
import br.com.estacionamento.domain.Veiculo;
import br.com.estacionamento.domain.dto.in.VeiculoFormDTO;
import br.com.estacionamento.repository.EstacionamentoRepository;
import br.com.estacionamento.repository.ModeloRepository;
import br.com.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private EstacionamentoRepository estacionamentoRepository;

    @Transactional
    public Veiculo criar(VeiculoFormDTO veiculoDTO) {
        Optional<Modelo> modeloEncontrado = modeloRepository.findById(veiculoDTO.getId_modelo());
        if (!modeloEncontrado.isPresent()){
            throw new RuntimeException("Modelo não existente");
        }


        Optional<Estacionamento> estacionamentoEncontrado = estacionamentoRepository.findById(veiculoDTO.getId_estacionamento());
        if (!estacionamentoEncontrado.isPresent()){
            throw new RuntimeException("Estacionamento não existe");
        }

        Optional<Veiculo> veiculoEncontrado = veiculoRepository.findByPlacaAndEstacionamento(veiculoDTO.getPlaca(), veiculoDTO.getId_estacionamento());
        if (veiculoEncontrado.isPresent()){
            throw new RuntimeException("Veiculo já cadastrado");
        }


        Modelo modelo = modeloEncontrado.get();
        Estacionamento estacionamento = estacionamentoEncontrado.get();
        Veiculo veiculo = veiculoDTO.converterParaVeiculo(modelo, estacionamento);
        Veiculo veiculoSalvo = veiculoRepository.save(veiculo);

        return veiculoSalvo;
    }

    public Veiculo buscarPelaPlaca(String placa, long estacionamentoId){
        Optional<Veiculo> veiculo = veiculoRepository.findByPlacaAndEstacionamento(placa, estacionamentoId);

        if (!veiculo.isPresent()){
            throw new RuntimeException("Veiculo não encontrado");
        }

        return veiculo.get();

    }

    @Transactional
    public void deletarPelaPlaca(String placa, long estacionamentoId){
        Optional<Veiculo> veiculo = veiculoRepository.findByPlacaAndEstacionamento(placa, estacionamentoId);

        if (!veiculo.isPresent()){
            throw new RuntimeException("Veiculo não encontrado");
        }

        veiculoRepository.delete(veiculo.get());
    }

}
