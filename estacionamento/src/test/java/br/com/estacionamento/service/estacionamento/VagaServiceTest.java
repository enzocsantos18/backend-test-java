package br.com.estacionamento.service.estacionamento;

import br.com.estacionamento.config.exception.DomainException;
import br.com.estacionamento.domain.dto.in.estacionamento.VagaFormDTO;
import br.com.estacionamento.domain.dto.out.estacionamento.RespostaVagaDTO;
import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.estacionamento.Estacionamento;
import br.com.estacionamento.domain.estacionamento.Vaga;
import br.com.estacionamento.domain.veiculo.TipoVeiculo;
import br.com.estacionamento.repository.estacionamento.EstacionamentoRepository;
import br.com.estacionamento.repository.estacionamento.MovimentacaoRepository;
import br.com.estacionamento.repository.estacionamento.VagaRepository;
import br.com.estacionamento.repository.veiculo.TipoVeiculoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class VagaServiceTest {
    @InjectMocks
    private VagaService vagaService;

    @Mock
    private VagaRepository vagaRepository;
    @Mock
    private EstacionamentoRepository estacionamentoRepository;
    @Mock
    private TipoVeiculoRepository tipoVeiculoRepository;
    @Mock
    private MovimentacaoRepository movimentacaoRepository;

    @Test
    void deveriaListarVagas() {
        Vaga vaga = getVaga();
        List<Vaga> vagas = Arrays.asList(vaga, vaga, vaga);

        when(vagaRepository.findByEstacionamentoId(anyLong())).thenReturn(vagas);

        List<RespostaVagaDTO> respostaVagas = vagaService.buscar(1L);

        assertTrue(respostaVagas.size() == vagas.size());
        assertTrue(respostaVagas.get(0).getQuantidade() == vagas.get(0).getQuantidade());
        assertTrue(respostaVagas.get(0).getId() == vagas.get(0).getId());
    }

    @Test
    void deveriaCriarVaga() {
        VagaFormDTO vagaFormDTO = getVagaFormDTO();
        Estacionamento estacionamento = getEstacionamento();
        TipoVeiculo tipoVeiculo = getTipoVeiculo();

        Optional<Estacionamento> estacionamentoOptional = Optional.of(estacionamento);
        Optional<TipoVeiculo> tipoVeiculoOptional = Optional.of(tipoVeiculo);
        Optional<Vaga> vagaOptional = Optional.empty();


        when(estacionamentoRepository.findById(anyLong())).thenReturn(estacionamentoOptional);
        when(tipoVeiculoRepository.findById(anyLong())).thenReturn(tipoVeiculoOptional);
        when(vagaRepository.findByEstacionamentoIdAndTipoId(anyLong(), anyLong())).thenReturn(vagaOptional);
        when(vagaRepository.save(any(Vaga.class))).thenReturn(getVaga());

        RespostaVagaDTO respostaVaga = vagaService.criar(vagaFormDTO, 1L);

        assertTrue(respostaVaga.getQuantidade() == vagaFormDTO.getQuantidade());
        assertTrue(respostaVaga.getTipo().getId() == vagaFormDTO.getTipo_id());
    }

    @Test
    void naoDeveriaCriarVaga() {
        VagaFormDTO vagaFormDTO = getVagaFormDTO();
        Estacionamento estacionamento = getEstacionamento();
        TipoVeiculo tipoVeiculo = getTipoVeiculo();

        Optional<Estacionamento> estacionamentoOptional = Optional.of(estacionamento);
        Optional<TipoVeiculo> tipoVeiculoOptional = Optional.of(tipoVeiculo);
        Optional<Vaga> vagaOptional = Optional.of(getVaga());

        when(estacionamentoRepository.findById(anyLong())).thenReturn(estacionamentoOptional);
        when(tipoVeiculoRepository.findById(anyLong())).thenReturn(tipoVeiculoOptional);
        when(vagaRepository.findByEstacionamentoIdAndTipoId(anyLong(), anyLong())).thenReturn(vagaOptional);
        when(vagaRepository.save(any(Vaga.class))).thenReturn(getVaga());

        assertThrows(DomainException.class, () -> vagaService.criar(vagaFormDTO, 1L));
    }

    @Test
    void deveriaAtualizarVaga() {
        VagaFormDTO vagaFormDTO = getVagaFormDTO();
        vagaFormDTO.setQuantidade(5);
        Estacionamento estacionamento = getEstacionamento();
        TipoVeiculo tipoVeiculo = getTipoVeiculo();

        Vaga vaga = getVaga();

        Optional<Estacionamento> estacionamentoOptional = Optional.of(estacionamento);
        Optional<TipoVeiculo> tipoVeiculoOptional = Optional.of(tipoVeiculo);
        Optional<Vaga> vagaOptional = Optional.of(vaga);

        Vaga vagaAtualizada = getVaga();
        vagaAtualizada.setQuantidade(5);


        when(estacionamentoRepository.findById(anyLong())).thenReturn(estacionamentoOptional);
        when(tipoVeiculoRepository.findById(anyLong())).thenReturn(tipoVeiculoOptional);
        when(movimentacaoRepository.contagemDeVeiculosPorTipoEmEstacionamento(anyLong(), anyLong())).thenReturn(2L);
        when(vagaRepository.findByEstacionamentoIdAndTipoId(anyLong(), anyLong())).thenReturn(vagaOptional);
        when(vagaRepository.save(any(Vaga.class))).thenReturn(vagaAtualizada);

        RespostaVagaDTO respostaVagaAtualizada = vagaService.atualizar(vagaFormDTO, 1L);

        assertTrue(respostaVagaAtualizada.getTipo().getId() == getTipoVeiculo().getId());
        assertTrue(respostaVagaAtualizada.getQuantidade() == 5);
        assertTrue(respostaVagaAtualizada.getId() == 1L);

    }

    @Test
    void naoDeveriaAtualizarVaga() {
        VagaFormDTO vagaFormDTO = getVagaFormDTO();
        vagaFormDTO.setQuantidade(5);
        Estacionamento estacionamento = getEstacionamento();
        TipoVeiculo tipoVeiculo = getTipoVeiculo();

        Vaga vaga = getVaga();

        Optional<Estacionamento> estacionamentoOptional = Optional.of(estacionamento);
        Optional<TipoVeiculo> tipoVeiculoOptional = Optional.of(tipoVeiculo);
        Optional<Vaga> vagaOptional = Optional.of(vaga);

        Vaga vagaAtualizada = getVaga();
        vagaAtualizada.setQuantidade(5);


        when(estacionamentoRepository.findById(anyLong())).thenReturn(estacionamentoOptional);
        when(tipoVeiculoRepository.findById(anyLong())).thenReturn(tipoVeiculoOptional);
        when(movimentacaoRepository.contagemDeVeiculosPorTipoEmEstacionamento(anyLong(), anyLong())).thenReturn(6L);
        when(vagaRepository.findByEstacionamentoIdAndTipoId(anyLong(), anyLong())).thenReturn(vagaOptional);
        when(vagaRepository.save(any(Vaga.class))).thenReturn(vagaAtualizada);


        assertThrows(DomainException.class, () -> vagaService.atualizar(vagaFormDTO, 1L));
    }

    @Test
    void deveriaDeletarVaga() {
        Vaga vaga = getVaga();
        Optional<Vaga> vagaOptional = Optional.of(vaga);

        when(vagaRepository.findByEstacionamentoIdAndTipoId(anyLong(), anyLong())).thenReturn(vagaOptional); //vaga
        when(movimentacaoRepository.contagemDeVeiculosPorTipoEmEstacionamento(anyLong(), anyLong())).thenReturn(0L);
        doNothing().when(vagaRepository).delete(vaga);

        vagaService.deletar(1L, 1L);

        verify(vagaRepository, times(1)).delete(vaga);

    }

    @Test
    void naoDeveriaDeletarVaga() {
        Vaga vaga = getVaga();
        Optional<Vaga> vagaOptional = Optional.of(vaga);

        when(vagaRepository.findByEstacionamentoIdAndTipoId(anyLong(), anyLong())).thenReturn(vagaOptional); //vaga
        when(movimentacaoRepository.contagemDeVeiculosPorTipoEmEstacionamento(anyLong(), anyLong())).thenReturn(1L);

        assertThrows(DomainException.class, () -> vagaService.deletar(1L, 1L));
    }

    @Test
    void deveriaGerarDomainExceptionVerificaQuantidadeParaAlteracao() {
        when(movimentacaoRepository.contagemDeVeiculosPorTipoEmEstacionamento(anyLong(), anyLong())).thenReturn(2L);
        assertThrows(DomainException.class, () -> vagaService.verificaQuantidadeParaAlteracao(1L, 1L, 1l));
    }

    @Test
    void deveriaGerarDomainExceptionVerificarDisponibilidadeTipoVaga() {
        Vaga vaga = getVaga();

        Optional<Vaga> vagaOptional = Optional.of(vaga);

        when(vagaRepository.findByEstacionamentoIdAndTipoId(anyLong(), anyLong())).thenReturn(vagaOptional);

        assertThrows(DomainException.class, () -> vagaService.verificarDisponibilidadeTipoVaga(1L, 1L));
    }

    private VagaFormDTO getVagaFormDTO() {
        VagaFormDTO vagaFormDTO = new VagaFormDTO();
        vagaFormDTO.setQuantidade(2);
        vagaFormDTO.setTipo_id(1L);

        return vagaFormDTO;
    }

    private TipoVeiculo getTipoVeiculo() {
        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setId(1L);
        tipoVeiculo.setTipo("Carro");

        return tipoVeiculo;
    }

    private Estacionamento getEstacionamento() {
        Estacionamento estacionamento = new Estacionamento();
        estacionamento.setId(1L);
        estacionamento.setNome("Estacionamento Teste");
        estacionamento.setEmpresa(new Empresa());

        return estacionamento;
    }

    private Vaga getVaga() {
        Vaga vaga = new Vaga();
        vaga.setId(1L);
        vaga.setQuantidade(2);
        vaga.setTipo(new TipoVeiculo());
        vaga.setEstacionamento(new Estacionamento());
        vaga.setTipo(getTipoVeiculo());
        return vaga;
    }
}