package br.com.estacionamento.repository.estacionamento;

import br.com.estacionamento.domain.estacionamento.EntradasSaidasHorarioRelatorio;
import br.com.estacionamento.domain.estacionamento.EntradasSaidasRelatorio;
import br.com.estacionamento.domain.estacionamento.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    Optional<Movimentacao> findFirstByVeiculoPlacaAndVeiculoEstacionamentoIdOrderByIdDesc(String placa, Long id);

    @Query("SELECT count(m) FROM Movimentacao m where m.veiculo.modelo.tipoVeiculo.id = ?1 and m.veiculo.estacionamento.id = ?2 and m.saida is null")
    Long contagemDeVeiculosPorTipoEmEstacionamento(Long tipo_id, Long estacionamento_id);


    @Query("SELECT new br.com.estacionamento.domain.estacionamento.EntradasSaidasRelatorio(count(m.entrada), count(m.saida)) FROM Movimentacao m where m.veiculo.estacionamento.id = ?3 and date(m.entrada) between ?1 and ?2")
    EntradasSaidasRelatorio gerarRelatorioPorData(Date dataInicial, Date dataFinal, Long estacionamentoId);


    @Query("SELECT \n" +
            "new br.com.estacionamento.domain.estacionamento.EntradasSaidasHorarioRelatorio(\n" +
            "time(M.entrada)," +
            "count(M.entrada)," +
            "count(M.saida))" +
            "FROM Movimentacao M WHERE M.veiculo.estacionamento.id = ?3 AND DATE(M.entrada) BETWEEN ?1 AND ?2 GROUP BY (HOUR(entrada))")
    List<EntradasSaidasHorarioRelatorio> gerarRelatorioHoraPorData(Date dataInicial, Date dataFinal, Long estacionamentoId);
}
