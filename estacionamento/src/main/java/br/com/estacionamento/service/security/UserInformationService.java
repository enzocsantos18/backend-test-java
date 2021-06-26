package br.com.estacionamento.service.security;

import br.com.estacionamento.domain.usuario.Usuario;
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserInformationService {

    public Usuario getUsuario(Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        return usuario;
    }

    public Long getEstacionamentoId(Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        return usuario.getEstacionamento().getId();
    }

    public Long getEmpresaId(Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        return usuario.getEmpresa().getId();
    }

}
