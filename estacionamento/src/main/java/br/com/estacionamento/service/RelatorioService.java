package br.com.estacionamento.service;

import br.com.estacionamento.domain.dto.in.RelatorioFormDTO;
import br.com.estacionamento.domain.relatorios.EntradasSaidasHorarioRelatorio;
import br.com.estacionamento.domain.relatorios.EntradasSaidasRelatorio;
import br.com.estacionamento.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    public EntradasSaidasRelatorio gerarEntradasSaidasPorPeriodo(RelatorioFormDTO dadosRelatorio){
        try {
            Date dataInicial=new SimpleDateFormat("yyyy-MM-dd").parse(dadosRelatorio.getDt_inicial());
            Date dataFinal =new SimpleDateFormat("yyyy-MM-dd").parse(dadosRelatorio.getDt_final());
            Long estacionamentoId = dadosRelatorio.getId_estacionamento();

            if (dataFinal.before(dataInicial)){
                throw new RuntimeException("Data inicial maior que data final");
            }

            EntradasSaidasRelatorio relatorio = movimentacaoRepository
                    .gerarRelatorioPorData(
                            dataInicial,
                            dataFinal,
                            estacionamentoId);
            return  relatorio;
        }catch (Exception e){
            throw new RuntimeException();
        }
    }
    public List<EntradasSaidasHorarioRelatorio> gerarEntradasSaidasPorHorario(RelatorioFormDTO dadosRelatorio){
        try {
            Date dataInicial=new SimpleDateFormat("yyyy-MM-dd").parse(dadosRelatorio.getDt_inicial());
            Date dataFinal =new SimpleDateFormat("yyyy-MM-dd").parse(dadosRelatorio.getDt_final());

            Long estacionamentoId = dadosRelatorio.getId_estacionamento();

            if (dataFinal.before(dataInicial)){
                throw new RuntimeException("Data inicial maior que data final");
            }

            List<EntradasSaidasHorarioRelatorio> relatorios = movimentacaoRepository
                    .gerarRelatorioHoraPorData(
                            dataInicial,
                            dataFinal,
                            estacionamentoId);
            return  relatorios;
        }catch (Exception e){
            throw new RuntimeException();
        }
    }
}
