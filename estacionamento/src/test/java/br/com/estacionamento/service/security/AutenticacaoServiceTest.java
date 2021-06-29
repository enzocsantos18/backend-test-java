package br.com.estacionamento.service.security;

import br.com.estacionamento.domain.usuario.Usuario;
import br.com.estacionamento.repository.usuario.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AutenticacaoServiceTest {

    @InjectMocks
    private AutenticacaoService autenticacaoService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    void deveriaEncontrarUmUsuario(){
        Usuario usuario = new Usuario();
        usuario.setNome("Teste");
        usuario.setEmail("teste@gmail.com");
        usuario.setSenha("123456");


        Optional<Usuario> usuarioOptional = Optional.of(usuario);

        when(usuarioRepository.findById("teste@gmail.com")).thenReturn(usuarioOptional);

        UserDetails userDetails = autenticacaoService.loadUserByUsername("teste@gmail.com");

        assertTrue(userDetails.getUsername() == "teste@gmail.com");
        assertTrue(userDetails.getPassword() == "123456");
    }

    @Test
    void naoDeveriaEncontrarUmUsuario(){
        Optional<Usuario> usuarioOptional = Optional.empty();
        when(usuarioRepository.findById("teste@gmail.com")).thenReturn(usuarioOptional);
        assertThrows(UsernameNotFoundException.class, () -> autenticacaoService.loadUserByUsername("teste@gmail.com"));
    }

}