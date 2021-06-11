package br.com.estacionamento.repository;

import br.com.estacionamento.domain.Estacionamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstacionamentoRepository extends JpaRepository<Estacionamento, Long> {
    List<Estacionamento> findByEmpresaId(long id);

    Optional<Estacionamento> findByEmpresaIdAndId(Long empresaId, Long estacionamentoID);
}
