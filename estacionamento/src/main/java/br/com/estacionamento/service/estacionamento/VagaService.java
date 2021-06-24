package br.com.estacionamento.service.estacionamento;

import br.com.estacionamento.domain.estacionamento.Estacionamento;
import br.com.estacionamento.domain.veiculo.TipoVeiculo;
import br.com.estacionamento.domain.estacionamento.Vaga;
import br.com.estacionamento.domain.dto.in.VagaFormDTO;
import br.com.estacionamento.config.exception.DomainException;
import br.com.estacionamento.config.exception.DomainNotFoundException;
import br.com.estacionamento.repository.estacionamento.EstacionamentoRepository;
import br.com.estacionamento.repository.veiculo.TipoVeiculoRepository;
import br.com.estacionamento.repository.estacionamento.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VagaService {

    @Autowired
    private VagaRepository vagaRepository;
    @Autowired
    private EstacionamentoRepository estacionamentoRepository;
    @Autowired
    private TipoVeiculoRepository tipoVeiculoRepository;


    public Vaga adicionar(VagaFormDTO dadosVaga, Long estacionamentoId) {

        Optional<Estacionamento> estacionamentoEncontrado = estacionamentoRepository.findById(estacionamentoId);

        if (!estacionamentoEncontrado.isPresent()){
            throw new DomainException("Estacionamento não encontrado!");
        }

        Optional<TipoVeiculo> tipoEncontrado = tipoVeiculoRepository.findById(dadosVaga.getTipo_id());
        if (!tipoEncontrado.isPresent()){
            throw new DomainException("Tipo de veiculo não encontrado!");
        }


        Estacionamento estacionamento = estacionamentoEncontrado.get();
        TipoVeiculo tipo = tipoEncontrado.get();

        Optional<Vaga> temVaga = vagaRepository.findByEstacionamentoIdAndTipoId(
                estacionamentoId,
                dadosVaga.getTipo_id());

        if (temVaga.isPresent()){
            throw new DomainException("Não é possível criar mais uma vaga para esse tipo");
        }

        Vaga vaga = dadosVaga.converterParaVaga(estacionamento, tipo);

        Vaga vagaCriada = vagaRepository.save(vaga);

        return vagaCriada;
    };

    public void deletar(Long estacionamentoId, Long tipoVagaId) {
        Optional<Vaga> vagaEncontrada = vagaRepository.findByEstacionamentoIdAndTipoId(estacionamentoId, tipoVagaId);
        if (!vagaEncontrada.isPresent()){
            throw new DomainNotFoundException("Vaga não encontrada");
        }

        Vaga vaga = vagaEncontrada.get();

        vagaRepository.delete(vaga);
    }

    public List<Vaga> buscar(Long estacionamentoId) {

        List<Vaga> vagas = vagaRepository.findByEstacionamentoId(estacionamentoId);

        return vagas;
    }

    public Vaga atualizar(VagaFormDTO dadosVaga, Long estacionamentoId) {
        Optional<Estacionamento> estacionamentoEncontrado = estacionamentoRepository.findById(
                estacionamentoId);

        if (!estacionamentoEncontrado.isPresent()){
            throw new DomainNotFoundException("Estacionamento não encontrado!");
        }

        Optional<TipoVeiculo> tipoEncontrado = tipoVeiculoRepository.findById(dadosVaga.getTipo_id());
        if (!tipoEncontrado.isPresent()){
            throw new DomainNotFoundException("Tipo de veiculo não encontrado!");
        }


        Optional<Vaga> temVaga = vagaRepository.findByEstacionamentoIdAndTipoId(
                estacionamentoId,
                dadosVaga.getTipo_id());

        if (!temVaga.isPresent()){
            throw new DomainNotFoundException("Vaga não encontrada");
        }

        Vaga vaga = temVaga.get();

        vaga.setQuantidade(dadosVaga.getQuantidade());

        Vaga vagaAtualizada = vagaRepository.save(vaga);

        return vagaAtualizada;
    }
}
