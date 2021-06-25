package br.com.estacionamento.repository.veiculo;

import br.com.estacionamento.domain.veiculo.Fabricante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FabricanteRepository extends JpaRepository<Fabricante, Long> {
}
