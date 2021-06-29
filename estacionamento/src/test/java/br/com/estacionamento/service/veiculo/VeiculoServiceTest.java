package br.com.estacionamento.service.veiculo;

import br.com.estacionamento.config.exception.DomainException;
import br.com.estacionamento.config.exception.DomainNotFoundException;
import br.com.estacionamento.domain.dto.in.veiculo.VeiculoFormDTO;
import br.com.estacionamento.domain.dto.out.veiculo.RespostaVeiculoDTO;
import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.estacionamento.Estacionamento;
import br.com.estacionamento.domain.estacionamento.Movimentacao;
import br.com.estacionamento.domain.veiculo.Modelo;
import br.com.estacionamento.domain.veiculo.Veiculo;
import br.com.estacionamento.repository.estacionamento.EstacionamentoRepository;
import br.com.estacionamento.repository.estacionamento.MovimentacaoRepository;
import br.com.estacionamento.repository.veiculo.ModeloRepository;
import br.com.estacionamento.repository.veiculo.VeiculoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class VeiculoServiceTest {

    @InjectMocks
    private VeiculoService veiculoService;

    @Mock
    private VeiculoRepository veiculoRepository;

    @Mock
    private ModeloRepository modeloRepository;

    @Mock
    private EstacionamentoRepository estacionamentoRepository;

    @Mock
    private MovimentacaoRepository movimentacaoRepository;

    @Test
    void deveriaRetornarVeiculo() {
        Veiculo veiculo = getVeiculo();
        Optional<Veiculo> veiculoOptional = Optional.of(veiculo);
        when(veiculoRepository.findByPlacaAndEstacionamento(anyString(), anyLong())).thenReturn(veiculoOptional);
        RespostaVeiculoDTO resposta = veiculoService.buscar(veiculo.getPlaca(), 1L);
        assertTrue(veiculo.getPlaca() == resposta.getPlaca());
        assertTrue(veiculo.getCor() == resposta.getCor());
    }

    @Test
    void naoDeveriaRetornarVeiculo() {
        Veiculo veiculo = getVeiculo();
        Optional<Veiculo> veiculoOptional = Optional.empty();
        when(veiculoRepository.findByPlacaAndEstacionamento(anyString(), anyLong())).thenReturn(veiculoOptional);
        assertThrows(DomainNotFoundException.class, () -> veiculoService.getVeiculo(veiculo.getPlaca(), 1L));
    }

    @Test
    void deveriaCriarVeiculo() {
        VeiculoFormDTO veiculoFormDTO = getVeiculoFormDTO();

        Modelo modelo = getModelo();
        Optional<Modelo> modeloOptional = Optional.of(modelo);

        Estacionamento estacionamento = getEstacionamento();
        Optional<Estacionamento> estacionamentoOptional = Optional.of(estacionamento);

        Veiculo veiculo = getVeiculo();
        Optional<Veiculo> optionalVeiculo = Optional.empty();

        when(modeloRepository.findById(anyLong())).thenReturn(modeloOptional);
        when(estacionamentoRepository.findById(anyLong())).thenReturn(estacionamentoOptional);
        when(veiculoRepository.findByPlacaAndEstacionamento(anyString(), anyLong())).thenReturn(optionalVeiculo);
        when(veiculoRepository.save(any(Veiculo.class))).thenReturn(veiculo);

        RespostaVeiculoDTO resposta = veiculoService.criar(veiculoFormDTO, 1L);

        assertTrue(resposta.getModelo().getId() == 1L);
        assertTrue(resposta.getPlaca() == "ABC1234");

    }

    @Test
    void naoDeveriaCriarVeiculo() {
        VeiculoFormDTO veiculoFormDTO = getVeiculoFormDTO();


        Modelo modelo = getModelo();
        Optional<Modelo> modeloOptional = Optional.of(modelo);

        Estacionamento estacionamento = getEstacionamento();
        Optional<Estacionamento> estacionamentoOptional = Optional.of(estacionamento);

        Veiculo veiculo = getVeiculo();
        Optional<Veiculo> optionalVeiculo = Optional.of(veiculo);

        when(modeloRepository.findById(anyLong())).thenReturn(modeloOptional);
        when(estacionamentoRepository.findById(anyLong())).thenReturn(estacionamentoOptional);
        when(veiculoRepository.findByPlacaAndEstacionamento(anyString(), anyLong())).thenReturn(optionalVeiculo);
        when(veiculoRepository.save(any(Veiculo.class))).thenReturn(veiculo);

        assertThrows(DomainException.class, () -> veiculoService.criar(veiculoFormDTO, 1L));
    }

    @Test
    void deveriaDeletarVeiculo() {
        Veiculo veiculo = getVeiculo();
        Movimentacao movimentacao = getMovimentacao();
        movimentacao.setSaida(LocalDateTime.now());

        Optional<Veiculo> veiculoOptional = Optional.of(veiculo);
        Optional<Movimentacao> movimentacaoOptional = Optional.of(movimentacao);

        when(veiculoRepository.findByPlacaAndEstacionamento(anyString(), anyLong())).thenReturn(veiculoOptional);
        when(movimentacaoRepository.findFirstByVeiculoPlacaAndVeiculoEstacionamentoIdOrderByIdDesc(anyString(), anyLong())).thenReturn(movimentacaoOptional);

        doNothing().when(veiculoRepository).delete(veiculo);

        veiculoService.deletar(veiculo.getPlaca(), 1L);

        verify(veiculoRepository, times(1)).delete(veiculo);

    }

    @Test
    void naoDeveriaDeletarVeiculo() {
        Veiculo veiculo = getVeiculo();
        Movimentacao movimentacao = getMovimentacao();

        Optional<Veiculo> veiculoOptional = Optional.of(veiculo);
        Optional<Movimentacao> movimentacaoOptional = Optional.of(movimentacao);

        when(veiculoRepository.findByPlacaAndEstacionamento(anyString(), anyLong())).thenReturn(veiculoOptional);
        when(movimentacaoRepository.findFirstByVeiculoPlacaAndVeiculoEstacionamentoIdOrderByIdDesc(anyString(), anyLong())).thenReturn(movimentacaoOptional);

        assertThrows(DomainException.class, () -> veiculoService.deletar(veiculo.getPlaca(), 1L));
    }

    @Test
    void deveriaRetornarDomainExceptioVerificaDisponibilidadeVeiculo() {
        VeiculoFormDTO veiculoFormDTO = new VeiculoFormDTO();
        veiculoFormDTO.setPlaca("ABC1234");
        veiculoFormDTO.setCor("Azul");
        veiculoFormDTO.setId_modelo(1L);


        Veiculo veiculo = getVeiculo();

        Optional<Veiculo> veiculoOptional = Optional.of(veiculo);

        when(veiculoRepository.findByPlacaAndEstacionamento(anyString(), anyLong()))
                .thenReturn(veiculoOptional);

        assertThrows(DomainException.class, () -> veiculoService.verificaDisponibilidadeVeiculo(veiculoFormDTO, 1L));
    }

    @Test
    void deveriaRetornarDomainExceptioVerificarSeEstaNoPatio() {

        Movimentacao movimentacao = getMovimentacao();

        Optional<Movimentacao> movimentacaoOptional = Optional.of(movimentacao);

        when(movimentacaoRepository.findFirstByVeiculoPlacaAndVeiculoEstacionamentoIdOrderByIdDesc(anyString(), anyLong()))
                .thenReturn(movimentacaoOptional);

        assertThrows(DomainException.class, () -> veiculoService.verificarSeEstaNoPatio("ABC1234", 1L));

    }

    private Movimentacao getMovimentacao() {
        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setId(1L);
        movimentacao.setVeiculo(new Veiculo());
        movimentacao.setEntrada(LocalDateTime.now());
        movimentacao.setSaida(null);
        return movimentacao;
    }

    private Veiculo getVeiculo() {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setPlaca("ABC1234");
        veiculo.setCor("Azul");
        veiculo.setEstacionamento(new Estacionamento());
        veiculo.setModelo(getModelo());
        return veiculo;
    }

    private Modelo getModelo() {
        Modelo modelo = new Modelo();
        modelo.setId(1L);
        modelo.setNome("Toro");

        return modelo;
    }

    private Estacionamento getEstacionamento() {
        Estacionamento estacionamento = new Estacionamento();
        estacionamento.setId(1L);
        estacionamento.setNome("Teste");
        estacionamento.setEmpresa(new Empresa());

        return estacionamento;
    }

    private VeiculoFormDTO getVeiculoFormDTO() {
        VeiculoFormDTO veiculoFormDTO = new VeiculoFormDTO();
        veiculoFormDTO.setId_modelo(1L);
        veiculoFormDTO.setPlaca("ABC1234");
        veiculoFormDTO.setCor("Azul");
        return veiculoFormDTO;
    }

}