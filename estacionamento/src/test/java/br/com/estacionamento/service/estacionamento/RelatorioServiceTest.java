package br.com.estacionamento.service.estacionamento;

import br.com.estacionamento.config.exception.DomainException;
import br.com.estacionamento.domain.dto.in.estacionamento.RelatorioFormDTO;
import br.com.estacionamento.domain.estacionamento.EntradasSaidasHorarioRelatorio;
import br.com.estacionamento.domain.estacionamento.EntradasSaidasRelatorio;
import br.com.estacionamento.repository.estacionamento.MovimentacaoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RelatorioServiceTest {

    @InjectMocks
    private RelatorioService relatorioService;
    @Mock
    private MovimentacaoRepository movimentacaoRepository;

    @Test
    void deveriaGerarRelatorioDeEntredasESaidasPorPeriodo() {
        EntradasSaidasRelatorio relatorio = new EntradasSaidasRelatorio(5L, 5L);
        RelatorioFormDTO relatorioFormDTO = new RelatorioFormDTO();
        relatorioFormDTO.setDt_inicial("2020-03-02");
        relatorioFormDTO.setDt_final("2020-03-04");

        when(movimentacaoRepository.gerarRelatorioPorData(any(Date.class), any(Date.class), anyLong())).thenReturn(relatorio);
        EntradasSaidasRelatorio relatorioGerado = relatorioService.gerarEntradasSaidasPorPeriodo(relatorioFormDTO, 1L);

        assertTrue(relatorioGerado.getSaidas() == relatorio.getSaidas());
        assertTrue(relatorioGerado.getEntradas() == relatorio.getEntradas());

    }

    @Test
    void naoDeveriaGerarRelatorioDeEntredasESaidasPorPeriodo() {
        EntradasSaidasRelatorio relatorio = new EntradasSaidasRelatorio(5L, 5L);
        RelatorioFormDTO relatorioFormDTO = new RelatorioFormDTO();
        relatorioFormDTO.setDt_inicial("2020-03-02");
        relatorioFormDTO.setDt_final("2020-03-01");

        when(movimentacaoRepository.gerarRelatorioPorData(any(Date.class), any(Date.class), anyLong())).thenReturn(relatorio);

        assertThrows(DomainException.class, () -> relatorioService.gerarEntradasSaidasPorPeriodo(relatorioFormDTO, 1L));
    }

    @Test
    void deveriaGerarRelatorioDeEntredasESaidasPorHorario() {
        EntradasSaidasHorarioRelatorio relatorio = new EntradasSaidasHorarioRelatorio(new Date(System.currentTimeMillis()), 80L, 10L);
        List<EntradasSaidasHorarioRelatorio> relatorios = Arrays.asList(relatorio, relatorio, relatorio);
        RelatorioFormDTO relatorioFormDTO = new RelatorioFormDTO();
        relatorioFormDTO.setDt_inicial("2020-03-02");
        relatorioFormDTO.setDt_final("2020-03-04");

        when(movimentacaoRepository.gerarRelatorioHoraPorData(any(Date.class), any(Date.class), anyLong())).thenReturn(relatorios);
        List<EntradasSaidasHorarioRelatorio> relatorioGerado = relatorioService.gerarEntradasSaidasPorHorario(relatorioFormDTO, 1L);

        assertTrue(relatorioGerado.size() == 3);
        assertTrue(relatorioGerado.get(0).getEntradas() == 80L);
        assertTrue(relatorioGerado.get(0).getSaidas() == 10L);
    }

    @Test
    void naoDeveriaGerarRelatorioDeEntredasESaidasPorHorario() {
        EntradasSaidasHorarioRelatorio relatorio = new EntradasSaidasHorarioRelatorio(new Date(System.currentTimeMillis()), 80L, 10L);
        List<EntradasSaidasHorarioRelatorio> relatorios = Arrays.asList(relatorio, relatorio, relatorio);
        RelatorioFormDTO relatorioFormDTO = new RelatorioFormDTO();
        relatorioFormDTO.setDt_inicial("2020-03-02");
        relatorioFormDTO.setDt_final("2020-03-01");


        when(movimentacaoRepository.gerarRelatorioHoraPorData(any(Date.class), any(Date.class), anyLong())).thenReturn(relatorios);

        assertThrows(DomainException.class, () -> relatorioService.gerarEntradasSaidasPorHorario(relatorioFormDTO, 1L));
    }


    @Test
    void deveriaGerarExcecaoAoChecarDatasOndeADataInicialMaiorQueAFinal() {
        try {
            Date dataInicial = relatorioService.parseDate("2020-04-01");
            Date dataFinal = relatorioService.parseDate("2020-03-01");

            assertThrows(DomainException.class, () -> relatorioService.checarData(dataInicial, dataFinal));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void deveriaRetornarDataCasoADataFornecidaEstejaNoFormatoCorreto() {
        try {
            String data = "2020-02-02";
            Date date = relatorioService.parseDate(data);

            assertTrue(date.getDay() == 0);
            assertTrue(date.getMonth() == 1);
            assertTrue(date.getYear() == 120);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}