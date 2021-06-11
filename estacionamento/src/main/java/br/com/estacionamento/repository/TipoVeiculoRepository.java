package br.com.estacionamento.repository;

import br.com.estacionamento.domain.TipoVeiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoVeiculoRepository extends JpaRepository<TipoVeiculo, Long> {
}
