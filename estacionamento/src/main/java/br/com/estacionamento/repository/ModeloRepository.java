package br.com.estacionamento.repository;

import br.com.estacionamento.domain.Empresa;
import br.com.estacionamento.domain.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VeiculoRepository extends JpaRepository<VeiculoRepository, Long> {
    Optional<Veiculo> findByPlaca(String placa);
}
