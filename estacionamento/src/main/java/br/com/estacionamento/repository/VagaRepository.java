package br.com.estacionamento.repository;

import br.com.estacionamento.domain.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {
    Optional<Vaga> findByEstacionamentoIdAndTipoId(Long estacionamento_id, Long tipo_id);
    List<Vaga> findByEstacionamentoIdAndEstacionamentoEmpresaId(Long estacionamento_id, Long empresa_id);
}
