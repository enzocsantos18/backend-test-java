package br.com.estacionamento.repository;

import br.com.estacionamento.domain.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    Optional<Movimentacao> findByVeiculoPlacaAndVeiculoEstacionamentoId(String placa, Long id);

    @Query("SELECT count(m) FROM Movimentacao m where m.veiculo.modelo.tipoVeiculo.id = ?1 and m.veiculo.estacionamento.id = ?2 and m.saida is null")
    Long contagemDeVeiculosPorTipoEmEstacionamento(Long tipo_id, Long estacionamento_id);
}
