package br.com.estacionamento.service.usuario;

import br.com.estacionamento.domain.usuario.TipoUsuario;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class TipoUsuarioServiceTest {

    @Mock
    private TipoUsuarioService tipoUsuarioService;

    @Test
    void deveriaListarTiposUsuario() {
        TipoUsuario tipoUsuario = new TipoUsuario();
        tipoUsuario.setId(1L);
        tipoUsuario.setNome("admin");
        when(tipoUsuarioService.listar()).thenReturn(Arrays.asList(tipoUsuario, tipoUsuario, tipoUsuario));
        List<TipoUsuario> tipos = tipoUsuarioService.listar();

        assertTrue(tipos.get(1).getNome() == "admin");
        assertTrue(tipos.get(1).getId() == 1L);
        assertTrue(tipos.size() == 3);
    }

    @Test
    void deveriaRetornarListaDeTiposDeUsuariosVazia() {
        when(tipoUsuarioService.listar()).thenReturn(Arrays.asList());
        List<TipoUsuario> tipos = tipoUsuarioService.listar();

        assertTrue(tipos.size() == 0);
    }

}