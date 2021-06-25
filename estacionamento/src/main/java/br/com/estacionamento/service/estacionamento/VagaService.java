package br.com.estacionamento.service.estacionamento;

import br.com.estacionamento.domain.estacionamento.Estacionamento;
import br.com.estacionamento.domain.veiculo.TipoVeiculo;
import br.com.estacionamento.domain.estacionamento.Vaga;
import br.com.estacionamento.domain.dto.in.VagaFormDTO;
import br.com.estacionamento.config.exception.DomainException;
import br.com.estacionamento.config.exception.DomainNotFoundException;
import br.com.estacionamento.repository.estacionamento.EstacionamentoRepository;
import br.com.estacionamento.repository.estacionamento.MovimentacaoRepository;
import br.com.estacionamento.repository.veiculo.TipoVeiculoRepository;
import br.com.estacionamento.repository.estacionamento.VagaRepository;
import org.jetbrains.annotations.NotNull;
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
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    public List<Vaga> buscar(Long estacionamentoId) {
        List<Vaga> vagas = vagaRepository.findByEstacionamentoId(estacionamentoId);
        return vagas;
    }

    public Vaga adicionar(VagaFormDTO dadosVaga, Long estacionamentoId) {

        Estacionamento estacionamento = getEstacionamento(estacionamentoId);
        TipoVeiculo tipo = getTipoVeiculo(dadosVaga.getTipo_id());

        verificarDisponibilidadeTipoVaga(dadosVaga, estacionamentoId);

        Vaga vaga = dadosVaga.converterParaVaga(estacionamento, tipo);
        Vaga vagaCriada = vagaRepository.save(vaga);

        return vagaCriada;
    }

    public Vaga atualizar(VagaFormDTO dadosVaga, Long estacionamentoId) {
        Estacionamento estacionamento = getEstacionamento(estacionamentoId);
        TipoVeiculo tipo = getTipoVeiculo(dadosVaga.getTipo_id());
        verificaQuantidadeParaAlteracao(estacionamentoId, dadosVaga.getTipo_id(), (long) dadosVaga.getQuantidade());
        Vaga vaga = getVaga(dadosVaga.getTipo_id(), estacionamentoId);
        vaga.setQuantidade(dadosVaga.getQuantidade());
        Vaga vagaAtualizada = vagaRepository.save(vaga);

        return vagaAtualizada;
    }

    public void deletar(Long estacionamentoId, Long tipoVagaId) {
        Vaga vaga = getVaga(tipoVagaId, estacionamentoId);

        verificaQuantidadeParaAlteracao(estacionamentoId, tipoVagaId, 0l);


        vagaRepository.delete(vaga);
    }

    private void verificaQuantidadeParaAlteracao(Long estacionamentoId, Long tipoVagaId, Long quantidade) {
        Long vagasOcupadas = movimentacaoRepository.contagemDeVeiculosPorTipoEmEstacionamento(tipoVagaId, estacionamentoId);

        if (vagasOcupadas > quantidade){
            throw new DomainException("Não será possível realizar a alteração devido a quantidade de veículos desse tipo no pátio");
        }
    }

    private Vaga getVaga(Long tipoId, Long estacionamentoId) {
        Optional<Vaga> temVaga = vagaRepository.findByEstacionamentoIdAndTipoId(
                estacionamentoId,
                tipoId);

        if (!temVaga.isPresent()){
            throw new DomainNotFoundException("Vaga não encontrada");
        }

        return temVaga.get();
    }

    private void verificarDisponibilidadeTipoVaga(VagaFormDTO dadosVaga, Long estacionamentoId) {
        Optional<Vaga> temVaga = vagaRepository.findByEstacionamentoIdAndTipoId(
                estacionamentoId,
                dadosVaga.getTipo_id());

        if (temVaga.isPresent()){
            throw new DomainException("Não é possível criar mais uma vaga para esse tipo");
        }
    }

    private TipoVeiculo getTipoVeiculo(Long tipoId) {
        Optional<TipoVeiculo> tipoEncontrado = tipoVeiculoRepository.findById(tipoId);
        if (!tipoEncontrado.isPresent()){
            throw new DomainException("Tipo de veiculo não encontrado!");
        }
        TipoVeiculo tipo = tipoEncontrado.get();
        return tipo;
    }

    private Estacionamento getEstacionamento(Long estacionamentoId) {
        Optional<Estacionamento> estacionamentoEncontrado = estacionamentoRepository.findById(estacionamentoId);

        if (!estacionamentoEncontrado.isPresent()){
            throw new DomainException("Estacionamento não encontrado!");
        }
        Estacionamento estacionamento = estacionamentoEncontrado.get();
        return estacionamento;
    }
}
