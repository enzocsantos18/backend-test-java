package br.com.estacionamento.service.veiculo;

import br.com.estacionamento.config.exception.DomainException;
import br.com.estacionamento.config.exception.DomainNotFoundException;
import br.com.estacionamento.domain.dto.in.veiculo.VeiculoFormDTO;
import br.com.estacionamento.domain.dto.out.veiculo.RespostaVeiculoDTO;
import br.com.estacionamento.domain.estacionamento.Estacionamento;
import br.com.estacionamento.domain.estacionamento.Movimentacao;
import br.com.estacionamento.domain.veiculo.Modelo;
import br.com.estacionamento.domain.veiculo.Veiculo;
import br.com.estacionamento.repository.estacionamento.EstacionamentoRepository;
import br.com.estacionamento.repository.estacionamento.MovimentacaoRepository;
import br.com.estacionamento.repository.veiculo.ModeloRepository;
import br.com.estacionamento.repository.veiculo.VeiculoRepository;
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

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    public RespostaVeiculoDTO buscar(String placa, long estacionamentoId) {
        Veiculo veiculo = getVeiculo(placa, estacionamentoId);
        RespostaVeiculoDTO veiculoResposta = new RespostaVeiculoDTO(veiculo);
        return veiculoResposta;
    }

    @Transactional
    public RespostaVeiculoDTO criar(VeiculoFormDTO veiculoDTO, Long estacionamentoId) {
        Modelo modelo = getModelo(veiculoDTO.getId_modelo());
        Estacionamento estacionamento = getEstacionamento(estacionamentoId);

        verificaDisponibilidadeVeiculo(veiculoDTO, estacionamentoId);

        Veiculo veiculo = veiculoDTO.converterParaVeiculo(modelo, estacionamento);

        Veiculo veiculoCriado = veiculoRepository.save(veiculo);
        RespostaVeiculoDTO veiculoResposta = new RespostaVeiculoDTO(veiculoCriado);
        return veiculoResposta;
    }

    @Transactional
    public void deletar(String placa, long estacionamentoId) {
        Veiculo veiculo = getVeiculo(placa, estacionamentoId);

        verificarSeEstaNoPatio(placa, estacionamentoId);

        veiculoRepository.delete(veiculo);
    }

    public void verificaDisponibilidadeVeiculo(VeiculoFormDTO veiculoDTO, Long estacionamentoId) {
        Optional<Veiculo> veiculoEncontrado = veiculoRepository.findByPlacaAndEstacionamento(veiculoDTO.getPlaca(), estacionamentoId);
        if (veiculoEncontrado.isPresent()) {
            throw new DomainException("Veiculo já cadastrado");
        }
    }

    public void verificarSeEstaNoPatio(String placa, Long estacionamentoId) {
        Optional<Movimentacao> movimentacao = movimentacaoRepository.findFirstByVeiculoPlacaAndVeiculoEstacionamentoIdOrderByIdDesc(placa, estacionamentoId);

        if (movimentacao.isPresent() && movimentacao.get().getSaida() == null) {
            throw new DomainException("O veiculo não deu saída");
        }

    }

    public Estacionamento getEstacionamento(Long estacionamentoId) {
        Optional<Estacionamento> estacionamentoEncontrado = estacionamentoRepository.findById(estacionamentoId);
        if (!estacionamentoEncontrado.isPresent()) {
            throw new DomainNotFoundException("Estacionamento não existe");
        }
        return estacionamentoEncontrado.get();
    }

    public Modelo getModelo(Long modeloId) {
        Optional<Modelo> modeloEncontrado = modeloRepository.findById(modeloId);
        if (!modeloEncontrado.isPresent()) {
            throw new DomainNotFoundException("Modelo não existente");
        }
        return modeloEncontrado.get();
    }

    public Veiculo getVeiculo(String placa, long estacionamentoId) {
        Optional<Veiculo> veiculo = veiculoRepository.findByPlacaAndEstacionamento(placa, estacionamentoId);

        if (!veiculo.isPresent()) {
            throw new DomainNotFoundException("Veiculo não encontrado");
        }

        return veiculo.get();
    }
}
