package br.com.estacionamento.repository;

import br.com.estacionamento.domain.estacionamento.Estacionamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstacionamentoRepository extends JpaRepository<Estacionamento, Long> {
}
