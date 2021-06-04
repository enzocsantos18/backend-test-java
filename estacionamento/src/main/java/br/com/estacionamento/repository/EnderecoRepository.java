package br.com.estacionamento.repository;

import br.com.estacionamento.domain.Endereco;
import br.com.estacionamento.domain.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
