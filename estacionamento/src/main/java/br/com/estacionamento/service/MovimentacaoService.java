package br.com.estacionamento.service;

import br.com.estacionamento.domain.*;
import br.com.estacionamento.domain.dto.in.MovimentacaoFormDTO;
import br.com.estacionamento.domain.exception.DomainException;
import br.com.estacionamento.domain.exception.DomainNotFoundException;
import br.com.estacionamento.repository.*;
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


    public Movimentacao entrada(MovimentacaoFormDTO dadosMovimentacao) {
        Optional<Veiculo> veiculoEncontrado = veiculoRepository.findByPlacaAndEstacionamento(dadosMovimentacao.getPlaca(), dadosMovimentacao.getId_estacionamento());
        if (!veiculoEncontrado.isPresent()){
            throw new DomainNotFoundException("Veiculo não encontrado");
        }

        Veiculo veiculo = veiculoEncontrado.get();

        Optional<Movimentacao> verificarMovimentacao = movimentacaoRepository.findFirstByVeiculoPlacaAndVeiculoEstacionamentoIdOrderByIdDesc(
                veiculo.getPlaca(),
                veiculo.getEstacionamento().getId()
        );



        if (verificarMovimentacao.isPresent() && verificarMovimentacao.get().getSaida() == null){
            throw new DomainException("O veículo ainda não deu saída");
        }


        Long quantidadeDeMovimentacoes = movimentacaoRepository.contagemDeVeiculosPorTipoEmEstacionamento(
                veiculo.getModelo().getTipoVeiculo().getId(),
                veiculo.getEstacionamento().getId()
        );
        Optional<Vaga> vaga = vagaRepository.findByEstacionamentoIdAndTipoId(
                veiculo.getEstacionamento().getId()
                , veiculo.getModelo().getTipoVeiculo().getId());


        if (!vaga.isPresent()){
            throw new DomainNotFoundException("Vaga não existe");
        }

        if (vaga.get().getQuantidade().longValue() == quantidadeDeMovimentacoes){
            throw new DomainException("Vagas cheias");
        }

        Movimentacao movimentacao = new Movimentacao();

        movimentacao.setEntrada(LocalDateTime.now());
        movimentacao.setVeiculo(veiculo);


        Movimentacao movimentacaoCriada = movimentacaoRepository.save(movimentacao);

        return movimentacaoCriada;
    };

    public Movimentacao saida(MovimentacaoFormDTO dadosMovimentacao) {
        Optional<Veiculo> veiculoEncontrado = veiculoRepository.findByPlacaAndEstacionamento(dadosMovimentacao.getPlaca(), dadosMovimentacao.getId_estacionamento());
        if (!veiculoEncontrado.isPresent()){
            throw new DomainNotFoundException("Veiculo não encontrado");
        }

        Veiculo veiculo = veiculoEncontrado.get();

        Optional<Movimentacao> verificarMovimentacao = movimentacaoRepository.findFirstByVeiculoPlacaAndVeiculoEstacionamentoIdOrderByIdDesc(
                veiculo.getPlaca(),
                veiculo.getEstacionamento().getId()
        );



        if (verificarMovimentacao.isPresent() && verificarMovimentacao.get().getSaida() != null){
            throw new DomainException("O veículo já deu saída");
        }
        Movimentacao movimentacao = verificarMovimentacao.get();

        movimentacao.setSaida(LocalDateTime.now());

        Movimentacao movimentacaoSaida = movimentacaoRepository.save(movimentacao);

        return movimentacaoSaida;
    }

}
