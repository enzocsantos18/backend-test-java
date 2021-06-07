package br.com.estacionamento.repository;

import br.com.estacionamento.domain.Empresa;
import br.com.estacionamento.domain.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    @Query("SELECT v FROM Veiculo v where v.placa = ?1 and v.estacionamento.id = ?2")
    Optional<Veiculo> findByPlacaAndEstacionamento(String placa, Long estacionamento);
}
