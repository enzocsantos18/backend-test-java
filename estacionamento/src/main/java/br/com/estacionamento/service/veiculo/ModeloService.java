package br.com.estacionamento.service.veiculo;

import br.com.estacionamento.domain.veiculo.Modelo;
import br.com.estacionamento.repository.veiculo.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModeloService {
    @Autowired
    private ModeloRepository modeloRepository;

    public List<Modelo> listar(Long fabricanteId) {
        List<Modelo> modelos;
        if (fabricanteId == null) {

            modelos = modeloRepository.findAll();
        } else {
            modelos = modeloRepository.findAllByFabricanteId(fabricanteId);
        }
        return modelos;
    }
}
