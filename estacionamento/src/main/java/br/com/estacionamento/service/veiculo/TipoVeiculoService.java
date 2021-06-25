package br.com.estacionamento.service.veiculo;

import br.com.estacionamento.config.exception.DomainException;
import br.com.estacionamento.config.exception.DomainNotFoundException;
import br.com.estacionamento.domain.dto.in.VagaFormDTO;
import br.com.estacionamento.domain.estacionamento.Estacionamento;
import br.com.estacionamento.domain.estacionamento.Vaga;
import br.com.estacionamento.domain.veiculo.TipoVeiculo;
import br.com.estacionamento.repository.estacionamento.EstacionamentoRepository;
import br.com.estacionamento.repository.estacionamento.MovimentacaoRepository;
import br.com.estacionamento.repository.estacionamento.VagaRepository;
import br.com.estacionamento.repository.veiculo.TipoVeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoVeiculoService {
    @Autowired
    private TipoVeiculoRepository tipoVeiculoRepository;

    public List<TipoVeiculo> listar() {
        List<TipoVeiculo> tipos = tipoVeiculoRepository.findAll();
        return tipos;
    }


}
