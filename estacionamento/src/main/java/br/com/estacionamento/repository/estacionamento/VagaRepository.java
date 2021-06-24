package br.com.estacionamento.repository.estacionamento;

import br.com.estacionamento.domain.estacionamento.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {
    Optional<Vaga> findByEstacionamentoIdAndTipoId(Long estacionamento_id, Long tipo_id);

    List<Vaga> findByEstacionamentoId(Long estacionamentoId);
}
