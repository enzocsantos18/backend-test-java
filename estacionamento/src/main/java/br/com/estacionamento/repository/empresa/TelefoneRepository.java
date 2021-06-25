package br.com.estacionamento.repository.empresa;

import br.com.estacionamento.domain.empresa.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
    Optional<Telefone> findByNumeroAndEmpresaId(String numero, Long empresaId);
}
