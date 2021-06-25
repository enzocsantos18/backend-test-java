package br.com.estacionamento.service.estacionamento;

import br.com.estacionamento.config.exception.DomainException;
import br.com.estacionamento.domain.dto.in.RelatorioFormDTO;
import br.com.estacionamento.domain.estacionamento.EntradasSaidasHorarioRelatorio;
import br.com.estacionamento.domain.estacionamento.EntradasSaidasRelatorio;
import br.com.estacionamento.repository.estacionamento.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    public EntradasSaidasRelatorio gerarEntradasSaidasPorPeriodo(RelatorioFormDTO dadosRelatorio, Long estacionamentoId) {
        try {
            Date dataInicial = parseDate(dadosRelatorio.getDt_inicial());
            Date dataFinal = parseDate(dadosRelatorio.getDt_final());

            checarData(dataInicial, dataFinal);

            return movimentacaoRepository
                    .gerarRelatorioPorData(
                            dataInicial,
                            dataFinal,
                            estacionamentoId);
        } catch (Exception e) {
            throw new DomainException("Confira novamente os dados fornecidos");
        }
    }

    public List<EntradasSaidasHorarioRelatorio> gerarEntradasSaidasPorHorario(RelatorioFormDTO dadosRelatorio, Long estacionamentoId) {
        try {
            Date dataInicial = parseDate(dadosRelatorio.getDt_inicial());
            Date dataFinal = parseDate(dadosRelatorio.getDt_final());

            checarData(dataInicial, dataFinal);

            return movimentacaoRepository
                    .gerarRelatorioHoraPorData(
                            dataInicial,
                            dataFinal,
                            estacionamentoId);
        } catch (Exception e) {
            throw new DomainException("Confira novamente os dados fornecidos");
        }
    }

    private void checarData(Date dataInicial, Date dataFinal) {
        if (dataFinal.before(dataInicial)) {
            throw new DomainException("Data inicial maior que data final");
        }
    }

    private Date parseDate(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(date);
    }
}
