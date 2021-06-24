package br.com.estacionamento.service.estacionamento;

import br.com.estacionamento.domain.dto.in.RelatorioFormDTO;
import br.com.estacionamento.config.exception.DomainException;
import br.com.estacionamento.domain.estacionamento.EntradasSaidasHorarioRelatorio;
import br.com.estacionamento.domain.estacionamento.EntradasSaidasRelatorio;
import br.com.estacionamento.repository.estacionamento.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    public EntradasSaidasRelatorio gerarEntradasSaidasPorPeriodo(RelatorioFormDTO dadosRelatorio, Long estacionamentoId) {
        try {
            Date dataInicial = new SimpleDateFormat("yyyy-MM-dd").parse(dadosRelatorio.getDt_inicial());
            Date dataFinal = new SimpleDateFormat("yyyy-MM-dd").parse(dadosRelatorio.getDt_final());

            if (dataFinal.before(dataInicial)) {
                throw new DomainException("Data inicial maior que data final");
            }

            EntradasSaidasRelatorio relatorio = movimentacaoRepository
                    .gerarRelatorioPorData(
                            dataInicial,
                            dataFinal,
                            estacionamentoId);
            return relatorio;
        } catch (Exception e) {
            throw new DomainException("Confira novamente os dados fornecidos");
        }
    }

    public List<EntradasSaidasHorarioRelatorio> gerarEntradasSaidasPorHorario(RelatorioFormDTO dadosRelatorio, Long estacionamentoId) {
        try {
            Date dataInicial = new SimpleDateFormat("yyyy-MM-dd").parse(dadosRelatorio.getDt_inicial());
            Date dataFinal = new SimpleDateFormat("yyyy-MM-dd").parse(dadosRelatorio.getDt_final());


            if (dataFinal.before(dataInicial)) {
                throw new DomainException("Data inicial maior que data final");
            }

            List<EntradasSaidasHorarioRelatorio> relatorios = movimentacaoRepository
                    .gerarRelatorioHoraPorData(
                            dataInicial,
                            dataFinal,
                            estacionamentoId);
            return relatorios;
        } catch (Exception e) {
            throw new DomainException("Confira novamente os dados fornecidos");
        }
    }
}
