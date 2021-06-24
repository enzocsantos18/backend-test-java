package br.com.estacionamento.repository.empresa;

import br.com.estacionamento.domain.empresa.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
