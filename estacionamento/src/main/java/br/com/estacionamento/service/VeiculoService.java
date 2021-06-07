package br.com.estacionamento.service;

import br.com.estacionamento.domain.Modelo;
import br.com.estacionamento.domain.Veiculo;
import br.com.estacionamento.domain.dto.in.VeiculoFormDTO;
import br.com.estacionamento.repository.ModeloRepository;
import br.com.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private ModeloRepository modeloRepository;

    public Veiculo criar(VeiculoFormDTO veiculoDTO) {
        Optional<Modelo> modeloEncontrado = modeloRepository.findById(veiculoDTO.getId_modelo());
        if (!modeloEncontrado.isPresent()){
            throw new RuntimeException("Modelo não existente");
        }

        Optional<Veiculo> veiculoEncontrado = veiculoRepository.findByPlaca(veiculoDTO.getPlaca());
        if (veiculoEncontrado.isPresent()){
            throw new RuntimeException("Veiculo já cadastrado");
        }

        Modelo modelo = modeloEncontrado.get();
        Veiculo veiculo = veiculoDTO.converterParaVeiculo(modelo);
        Veiculo veiculoSalvo = veiculoRepository.save(veiculo);

        return veiculoSalvo;
    }

}
