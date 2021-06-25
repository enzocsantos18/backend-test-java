package br.com.estacionamento.service.veiculo;

import br.com.estacionamento.domain.veiculo.Fabricante;
import br.com.estacionamento.repository.veiculo.FabricanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FabricanteService {
    @Autowired
    private FabricanteRepository fabricanteRepository;

    public List<Fabricante> listar() {
        List<Fabricante> fabricantes = fabricanteRepository.findAll();
        return fabricantes;
    }


}
