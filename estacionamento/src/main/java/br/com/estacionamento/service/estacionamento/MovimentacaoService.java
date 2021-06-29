package br.com.estacionamento.service.estacionamento;

import br.com.estacionamento.config.exception.DomainException;
import br.com.estacionamento.config.exception.DomainNotFoundException;
import br.com.estacionamento.domain.dto.in.estacionamento.MovimentacaoFormDTO;
import br.com.estacionamento.domain.dto.out.estacionamento.RespostaMovimentacaoDTO;
import br.com.estacionamento.domain.estacionamento.Movimentacao;
import br.com.estacionamento.domain.estacionamento.Vaga;
import br.com.estacionamento.domain.veiculo.Veiculo;
import br.com.estacionamento.repository.estacionamento.MovimentacaoRepository;
import br.com.estacionamento.repository.estacionamento.VagaRepository;
import br.com.estacionamento.repository.veiculo.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    @Autowired
    private VeiculoRepository veiculoRepository;
    @Autowired
    private VagaRepository vagaRepository;

    public RespostaMovimentacaoDTO entrada(MovimentacaoFormDTO dadosMovimentacao, Long estacionamentoId) {
        Veiculo veiculo = getVeiculo(dadosMovimentacao.getPlaca(), estacionamentoId);
        verificaDisponibilidadeDeVaga(veiculo);
        Movimentacao movimentacao = verificaEntrada(veiculo);

        Movimentacao movimentacaoCriada = movimentacaoRepository.save(movimentacao);

        RespostaMovimentacaoDTO movimentacaoResposta = new RespostaMovimentacaoDTO(movimentacaoCriada);
        return movimentacaoResposta;

    }

    public RespostaMovimentacaoDTO saida(MovimentacaoFormDTO dadosMovimentacao, Long estacionamentoId) {
        Veiculo veiculo = getVeiculo(dadosMovimentacao.getPlaca(), estacionamentoId);
        Movimentacao movimentacao = verificaSaida(veiculo);

        Movimentacao movimentacaoCriada = movimentacaoRepository.save(movimentacao);
        RespostaMovimentacaoDTO movimentacaoResposta = new RespostaMovimentacaoDTO(movimentacaoCriada);
        return movimentacaoResposta;
    }

    public Movimentacao verificaEntrada(Veiculo veiculo) {
        Optional<Movimentacao> verificarMovimentacao = movimentacaoRepository.findFirstByVeiculoPlacaAndVeiculoEstacionamentoIdOrderByIdDesc(
                veiculo.getPlaca(),
                veiculo.getEstacionamento().getId()
        );

        if (verificarMovimentacao.isPresent() && verificarMovimentacao.get().getSaida() == null) {
            throw new DomainException("O veículo ainda não deu saída");
        }

        Movimentacao movimentacao = new Movimentacao();

        movimentacao.setEntrada(LocalDateTime.now());
        movimentacao.setVeiculo(veiculo);
        return movimentacao;
    }

    public Movimentacao verificaSaida(Veiculo veiculo) {
        Optional<Movimentacao> verificarMovimentacao = movimentacaoRepository.findFirstByVeiculoPlacaAndVeiculoEstacionamentoIdOrderByIdDesc(
                veiculo.getPlaca(),
                veiculo.getEstacionamento().getId()
        );


        if (verificarMovimentacao.isPresent() && verificarMovimentacao.get().getSaida() != null) {
            throw new DomainException("O veículo já deu saída");
        }
        Movimentacao movimentacao = verificarMovimentacao.get();

        movimentacao.setSaida(LocalDateTime.now());
        return movimentacao;
    }

    public Veiculo getVeiculo(String placa, Long estacionamentoId) {
        Optional<Veiculo> veiculoEncontrado = veiculoRepository.findByPlacaAndEstacionamento(placa, estacionamentoId);
        if (!veiculoEncontrado.isPresent()) {
            throw new DomainNotFoundException("Veiculo não encontrado");
        }

        return veiculoEncontrado.get();
    }

    public void verificaDisponibilidadeDeVaga(Veiculo veiculo) {
        Long quantidadeDeMovimentacoes = movimentacaoRepository.contagemDeVeiculosPorTipoEmEstacionamento(
                veiculo.getModelo().getTipoVeiculo().getId(),
                veiculo.getEstacionamento().getId()
        );
        Optional<Vaga> vaga = vagaRepository.findByEstacionamentoIdAndTipoId(
                veiculo.getEstacionamento().getId()
                , veiculo.getModelo().getTipoVeiculo().getId());


        if (!vaga.isPresent()) {
            throw new DomainNotFoundException("Vaga não existe");
        }

        if (vaga.get().getQuantidade().longValue() == quantidadeDeMovimentacoes) {
            throw new DomainException("Vagas cheias");
        }
    }
}
