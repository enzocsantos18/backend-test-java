package br.com.estacionamento.service.veiculo;

import br.com.estacionamento.domain.veiculo.TipoVeiculo;
import br.com.estacionamento.repository.veiculo.TipoVeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoVeiculoService {
    @Autowired
    private TipoVeiculoRepository tipoVeiculoRepository;

    public List<TipoVeiculo> listar() {
        return tipoVeiculoRepository.findAll();
    }
}
