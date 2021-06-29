package br.com.estacionamento.service.estacionamento;

import br.com.estacionamento.config.exception.DomainException;
import br.com.estacionamento.config.exception.DomainNotFoundException;
import br.com.estacionamento.domain.dto.in.estacionamento.MovimentacaoFormDTO;
import br.com.estacionamento.domain.dto.out.estacionamento.RespostaMovimentacaoDTO;
import br.com.estacionamento.domain.estacionamento.Estacionamento;
import br.com.estacionamento.domain.estacionamento.Movimentacao;
import br.com.estacionamento.domain.estacionamento.Vaga;
import br.com.estacionamento.domain.veiculo.Modelo;
import br.com.estacionamento.domain.veiculo.TipoVeiculo;
import br.com.estacionamento.domain.veiculo.Veiculo;
import br.com.estacionamento.repository.estacionamento.MovimentacaoRepository;
import br.com.estacionamento.repository.estacionamento.VagaRepository;
import br.com.estacionamento.repository.veiculo.VeiculoRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class MovimentacaoServiceTest {
    @InjectMocks
    private MovimentacaoService movimentacaoService;

    @Mock
    private MovimentacaoRepository movimentacaoRepository;
    @Mock
    private VeiculoRepository veiculoRepository;
    @Mock
    private VagaRepository vagaRepository;


    @Test
    void deveriaDarEntrada() {

        MovimentacaoFormDTO movimentacaoFormDTO = getMovimentacaoFormDTO();

        Veiculo veiculo = getVeiculo();
        Movimentacao movimentacao = getMovimentacao();
        Vaga vaga = getVaga();

        Optional<Vaga> optionalVaga = Optional.of(vaga);

        Optional<Veiculo> optionalVeiculo = Optional.of(veiculo);
        Optional<Movimentacao> movimentacaoOptional = Optional.empty();

        when(veiculoRepository.findByPlacaAndEstacionamento(anyString(), anyLong())).thenReturn(optionalVeiculo);
        when(movimentacaoRepository.contagemDeVeiculosPorTipoEmEstacionamento(anyLong(), anyLong())).thenReturn(1L);
        when(vagaRepository.findByEstacionamentoIdAndTipoId(anyLong(), anyLong())).thenReturn(optionalVaga);
        when(movimentacaoRepository.findFirstByVeiculoPlacaAndVeiculoEstacionamentoIdOrderByIdDesc(anyString(), anyLong()))
                .thenReturn(movimentacaoOptional);
        when(movimentacaoRepository.save(any(Movimentacao.class))).thenReturn(movimentacao);

        RespostaMovimentacaoDTO respostaEntrada = movimentacaoService.entrada(movimentacaoFormDTO, 1L);

        assertTrue(respostaEntrada.getEntrada() != null);
        assertTrue(respostaEntrada.getSaida() == null);

    }

    @Test
    void deveriaDarSaida() {
        MovimentacaoFormDTO movimentacaoFormDTO = getMovimentacaoFormDTO();

        Veiculo veiculo = getVeiculo();

        Movimentacao movimentacao = getMovimentacao();
        movimentacao.setSaida(LocalDateTime.now());

        Vaga vaga = getVaga();

        Optional<Vaga> optionalVaga = Optional.of(vaga);

        Optional<Veiculo> optionalVeiculo = Optional.of(veiculo);

        when(veiculoRepository.findByPlacaAndEstacionamento(anyString(), anyLong())).thenReturn(optionalVeiculo);
        when(vagaRepository.findByEstacionamentoIdAndTipoId(anyLong(), anyLong())).thenReturn(optionalVaga);
        when(movimentacaoRepository.save(any(Movimentacao.class))).thenReturn(movimentacao);

        RespostaMovimentacaoDTO respostaEntrada = movimentacaoService.entrada(movimentacaoFormDTO, 1L);

        assertTrue(respostaEntrada.getEntrada() != null);
        assertTrue(respostaEntrada.getSaida() != null);
    }

    @Test
    void verificaDarEntradaDeveriaRetornarMovimentacao() {
        Veiculo veiculo = getVeiculo();

        Optional<Movimentacao> movimentacaoOptional = Optional.empty();

        when(movimentacaoRepository.findFirstByVeiculoPlacaAndVeiculoEstacionamentoIdOrderByIdDesc(veiculo.getPlaca(), veiculo.getEstacionamento().getId()))
                .thenReturn(movimentacaoOptional);

        Movimentacao movimentacao = movimentacaoService.verificaEntrada(veiculo);

        assertTrue(movimentacao.getEntrada() != null);
        assertTrue(movimentacao.getSaida() == null);
        assertTrue(movimentacao.getVeiculo().getPlaca() == veiculo.getPlaca());

    }

    @Test
    void verificaDarEntradaNaoDeveriaRetornarMovimentacao() {
        Veiculo veiculo = getVeiculo();

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setEntrada(LocalDateTime.now());
        movimentacao.setVeiculo(veiculo);
        movimentacao.setId(1L);

        Optional<Movimentacao> movimentacaoOptional = Optional.of(movimentacao);

        when(movimentacaoRepository.findFirstByVeiculoPlacaAndVeiculoEstacionamentoIdOrderByIdDesc(veiculo.getPlaca(), veiculo.getEstacionamento().getId()))
                .thenReturn(movimentacaoOptional);

        assertThrows(DomainException.class,() -> movimentacaoService.verificaEntrada(veiculo));

    }

    @Test
    void verificaDarSaidaDeveriaRetornarMovimentacao() {
        Veiculo veiculo = getVeiculo();

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setEntrada(LocalDateTime.now());
        movimentacao.setVeiculo(veiculo);
        movimentacao.setId(1L);

        Optional<Movimentacao> movimentacaoOptional = Optional.of(movimentacao);

        when(movimentacaoRepository.findFirstByVeiculoPlacaAndVeiculoEstacionamentoIdOrderByIdDesc(veiculo.getPlaca(), veiculo.getEstacionamento().getId()))
                .thenReturn(movimentacaoOptional);

        Movimentacao movimentacaoEncontrada = movimentacaoService.verificaSaida(veiculo);


        assertTrue(movimentacaoEncontrada.getEntrada() != null);
        assertTrue(movimentacaoEncontrada.getSaida() != null);
        assertTrue(movimentacaoEncontrada.getVeiculo().getPlaca() == veiculo.getPlaca());
    }

    @Test
    void verificaDarSaidaNaoDeveriaRetornarMovimentacao() {
        Veiculo veiculo = getVeiculo();

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setVeiculo(veiculo);
        movimentacao.setId(1L);
        movimentacao.setEntrada(LocalDateTime.now());
        movimentacao.setSaida(LocalDateTime.now());

        Optional<Movimentacao> movimentacaoOptional = Optional.of(movimentacao);

        when(movimentacaoRepository.findFirstByVeiculoPlacaAndVeiculoEstacionamentoIdOrderByIdDesc(veiculo.getPlaca(), veiculo.getEstacionamento().getId()))
                .thenReturn(movimentacaoOptional);

        assertThrows(DomainException.class, () -> movimentacaoService.verificaSaida(veiculo));
    }

    @Test
    void verificaDisponibilidadeDeVagaDeveriaRetornarDomainNotFoundException() {
        Veiculo veiculo = getVeiculo();
        Optional<Vaga> vagaOptional = Optional.empty();

        when(movimentacaoRepository.contagemDeVeiculosPorTipoEmEstacionamento(anyLong(), anyLong())).thenReturn(5L);
        when(vagaRepository.findByEstacionamentoIdAndTipoId(anyLong(), anyLong())).thenReturn(vagaOptional);


        assertThrows(DomainNotFoundException.class, () -> movimentacaoService.verificaDisponibilidadeDeVaga(veiculo));
    }

    @Test
    void verificaDisponibilidadeDeVagaDeveriaRetornarDomainException() {
        Veiculo veiculo = getVeiculo();

        Vaga vaga = new Vaga();

        vaga.setEstacionamento(getEstacionamento());
        vaga.setId(1L);
        vaga.setQuantidade(5);
        vaga.setTipo(getTipoVeiculo());

        Optional<Vaga> vagaOptional = Optional.of(vaga);

        when(movimentacaoRepository.contagemDeVeiculosPorTipoEmEstacionamento(anyLong(), anyLong())).thenReturn(5L);
        when(vagaRepository.findByEstacionamentoIdAndTipoId(anyLong(), anyLong())).thenReturn(vagaOptional);


        assertThrows(DomainException.class, () -> movimentacaoService.verificaDisponibilidadeDeVaga(veiculo));
    }

    @Test
    void deveriaRetornarVeiculo() {
        String placa = "ABC1234";
        Veiculo veiculo = getVeiculo();

        Optional<Veiculo> optionalVeiculo = Optional.of(veiculo);

        when(veiculoRepository.findByPlacaAndEstacionamento(placa, 1L)).thenReturn(optionalVeiculo);

        Veiculo veiculoEncontrado = movimentacaoService.getVeiculo(placa, 1L);

        assertTrue(veiculoEncontrado.getPlaca() == placa);
    }

    @Test
    void naoDeveriaRetornarVeiculo() {
        String placa = "ABC1234";
        Optional<Veiculo> optionalVeiculo = Optional.empty();
        when(veiculoRepository.findByPlacaAndEstacionamento(placa, 1L)).thenReturn(optionalVeiculo);

        assertThrows(DomainNotFoundException.class, () -> movimentacaoService.getVeiculo(placa, 1L));
    }

    private Veiculo getVeiculo(){
        Modelo modelo = new Modelo();
        modelo.setNome("Toro");
        modelo.setId(1L);
        modelo.setTipoVeiculo(getTipoVeiculo());

        Estacionamento estacionamento = getEstacionamento();

        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setCor("Azul");
        veiculo.setPlaca("ABC1234");
        veiculo.setModelo(modelo);
        veiculo.setEstacionamento(estacionamento);

        return veiculo;
    }

    private Estacionamento getEstacionamento() {
        Estacionamento estacionamento = new Estacionamento();
        estacionamento.setId(1L);

        return estacionamento;
    }

    private TipoVeiculo getTipoVeiculo() {
        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setId(1L);

        return tipoVeiculo;
    }

    private MovimentacaoFormDTO getMovimentacaoFormDTO() {
        MovimentacaoFormDTO movimentacaoFormDTO = new MovimentacaoFormDTO();
        movimentacaoFormDTO.setPlaca("ABC1234");
        return movimentacaoFormDTO;
    }

    private Vaga getVaga() {
        Vaga vaga = new Vaga();

        vaga.setTipo(getTipoVeiculo());
        vaga.setQuantidade(5);
        vaga.setEstacionamento(getEstacionamento());
        vaga.setId(1L);
        return vaga;
    }

    private Movimentacao getMovimentacao() {
        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setEntrada(LocalDateTime.now());
        movimentacao.setVeiculo(getVeiculo());
        movimentacao.setId(1L);
        return movimentacao;
    }
}