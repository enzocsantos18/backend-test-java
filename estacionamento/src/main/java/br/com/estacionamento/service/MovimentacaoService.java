package br.com.estacionamento.service;

import br.com.estacionamento.domain.*;
import br.com.estacionamento.domain.dto.in.MovimentacaoFormDTO;
import br.com.estacionamento.domain.dto.in.VagaFormDTO;
import br.com.estacionamento.repository.*;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
            throw new RuntimeException("Veiculo não encontrado");
        }

        Veiculo veiculo = veiculoEncontrado.get();

        Optional<Movimentacao> verificarMovimentacao = movimentacaoRepository.findByVeiculoPlacaAndVeiculoEstacionamentoId(
                veiculo.getPlaca(),
                veiculo.getEstacionamento().getId()
        );



        if (verificarMovimentacao.isPresent() && verificarMovimentacao.get().getSaida() == null){
            throw new RuntimeException("O veículo ainda não deu saída");
        }


        Long quantidadeDeMovimentacoes = movimentacaoRepository.contagemDeVeiculosPorTipoEmEstacionamento(
                veiculo.getModelo().getTipoVeiculo().getId(),
                veiculo.getEstacionamento().getId()
        );
        Optional<Vaga> vaga = vagaRepository.findByEstacionamentoIdAndTipoId(
                veiculo.getEstacionamento().getId()
                , veiculo.getModelo().getTipoVeiculo().getId());


        if (!vaga.isPresent()){
            throw new RuntimeException("Vaga não existe");
        }

        if (vaga.get().getQuantidade().longValue() == quantidadeDeMovimentacoes){
            throw new RuntimeException("Vagas cheias");
        }

        Movimentacao movimentacao = new Movimentacao();

        movimentacao.setEntrada(LocalDateTime.now());
        movimentacao.setVeiculo(veiculo);


        Movimentacao movimentacaoCriada = movimentacaoRepository.save(movimentacao);

        return movimentacaoCriada;
    };


}
