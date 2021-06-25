package br.com.estacionamento.service.security;

import br.com.estacionamento.domain.usuario.Usuario;
import br.com.estacionamento.repository.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findById(email);
        if (!usuario.isPresent()) {
            throw new UsernameNotFoundException("Email e/ou senha inválidos");
        }
        return usuario.get();
    }
}
