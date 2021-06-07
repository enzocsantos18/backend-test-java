package br.com.estacionamento.repository;

import br.com.estacionamento.domain.Estacionamento;
import br.com.estacionamento.domain.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstacionamentoRepository extends JpaRepository<Estacionamento, Long> {
}
