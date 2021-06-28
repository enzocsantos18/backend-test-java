package br.com.estacionamento.repository.usuario;

import br.com.estacionamento.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    List<Usuario> findByEstacionamentoId(Long empresaId);
}
