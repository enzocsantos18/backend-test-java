package br.com.estacionamento.repository.veiculo;

import br.com.estacionamento.domain.veiculo.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Long> {
    List<Modelo> findAllByFabricanteId(Long fabricanteId);
}
