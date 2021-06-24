package br.com.estacionamento.repository.veiculo;

import br.com.estacionamento.domain.veiculo.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Long> {
}
