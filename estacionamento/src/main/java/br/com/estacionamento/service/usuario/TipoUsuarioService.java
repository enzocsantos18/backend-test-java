package br.com.estacionamento.service.usuario;

import br.com.estacionamento.domain.usuario.TipoUsuario;
import br.com.estacionamento.repository.usuario.TipoUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoUsuarioService {
    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    public List<TipoUsuario> listar() {
        List<TipoUsuario> tipos = tipoUsuarioRepository.findAll();
        return tipos;
    }
}
