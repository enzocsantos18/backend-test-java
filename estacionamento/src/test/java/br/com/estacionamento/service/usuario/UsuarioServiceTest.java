package br.com.estacionamento.service.usuario;

import br.com.estacionamento.config.exception.DomainException;
import br.com.estacionamento.config.exception.DomainNotFoundException;
import br.com.estacionamento.domain.dto.in.usuario.UsuarioFormDTO;
import br.com.estacionamento.domain.dto.in.usuario.UsuarioFormUpdateDTO;
import br.com.estacionamento.domain.dto.out.usuario.RespostaUsuarioDTO;
import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.estacionamento.Estacionamento;
import br.com.estacionamento.domain.usuario.TipoUsuario;
import br.com.estacionamento.domain.usuario.Usuario;
import br.com.estacionamento.repository.estacionamento.EstacionamentoRepository;
import br.com.estacionamento.repository.usuario.TipoUsuarioRepository;
import br.com.estacionamento.repository.usuario.UsuarioRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private EstacionamentoRepository estacionamentoRepository;
    @Mock
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Test
    void deveriaListarUsuarios(){
        Usuario usuario = getUsuario();
        Estacionamento estacionamento = getEstacionamento();

        Optional<Estacionamento> estacionamentoOptional = Optional.of(estacionamento);
        List<Usuario> usuarios = Arrays.asList(usuario, usuario, usuario);

        when(estacionamentoRepository.findById(anyLong())).thenReturn(estacionamentoOptional);
        when(usuarioRepository.findByEstacionamentoId(anyLong())).thenReturn(usuarios);
        List<RespostaUsuarioDTO> lista = usuarioService.listar(usuario, estacionamento.getId());

        assertTrue(lista.get(0).getEmail() == "teste@teste.com");
        assertTrue(lista.get(1).getEmail() == "teste@teste.com");
        assertTrue(lista.get(2).getEmail() == "teste@teste.com");
        assertTrue(lista.size() == 3);

    }

    @Test
    void naoDeveriaListarUsuariosCasoUsuarioLogadoNaoSejaDoEstacionamento(){
        Usuario usuario = getUsuario();
        Estacionamento estacionamento = getEstacionamento();
        estacionamento.setId(2L);

        Optional<Estacionamento> estacionamentoOptional = Optional.of(estacionamento);
        List<Usuario> usuarios = Arrays.asList(usuario, usuario, usuario);

        when(estacionamentoRepository.findById(anyLong())).thenReturn(estacionamentoOptional);
        when(usuarioRepository.findByEstacionamentoId(anyLong())).thenReturn(usuarios);

        assertThrows(DomainException.class, () -> usuarioService.listar(usuario, estacionamento.getId()));
    }

    @Test
    void naoDeveriaListarUsuariosCasoUsuarioLogadoNaoSejaDaEmpresa(){
        Usuario usuario = getUsuario();
        Empresa empresa = getEmpresa();
        empresa.setId(2L);
        usuario.setEmpresa(empresa);

        Estacionamento estacionamento = getEstacionamento();

        Optional<Estacionamento> estacionamentoOptional = Optional.of(estacionamento);
        List<Usuario> usuarios = Arrays.asList(usuario, usuario, usuario);

        when(estacionamentoRepository.findById(anyLong())).thenReturn(estacionamentoOptional);
        when(usuarioRepository.findByEstacionamentoId(anyLong())).thenReturn(usuarios);

        assertThrows(DomainException.class, () -> usuarioService.listar(usuario, estacionamento.getId()));
    }


    @Test
    void deveriaCriarUsuario(){
        Usuario usuario = getUsuario();
        Estacionamento estacionamento = getEstacionamento();
        TipoUsuario tipoUsuario = getTipoUsuario();


        Optional<Estacionamento> estacionamentoOptional = Optional.of(estacionamento);
        Optional<TipoUsuario> optionalTipoUsuario = Optional.of(tipoUsuario);
        Optional<Usuario> optionalUsuario = Optional.empty();

        when(estacionamentoRepository.findById(anyLong())).thenReturn(estacionamentoOptional);
        when(tipoUsuarioRepository.findById(anyLong())).thenReturn(optionalTipoUsuario);
        when(usuarioRepository.findById(anyString())).thenReturn(optionalUsuario);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioFormDTO usuarioFormDTO = getUsuarioFormDTO();

        RespostaUsuarioDTO respostaUsuario = usuarioService.criar(usuarioFormDTO, 1L);

        assertTrue(respostaUsuario.getEmail() == usuario.getEmail());
        assertTrue(respostaUsuario.getNome() == usuario.getNome());
    }



    @Test
    void naoDeveriaCriarUsuarioQuandoOtipoDoUsuarioIgualAAdmin(){
        UsuarioFormUpdateDTO usuarioFormUpdateDTO = getUsuarioFormUpdateDTO();
        Usuario usuario = getUsuario();
        usuario.setTipos(getTipoUsuarioAdmin());
        Estacionamento estacionamento = getEstacionamento();
        TipoUsuario tipoUsuario = getTipoUsuarioAdmin();

        Optional<Estacionamento> estacionamentoOptional = Optional.of(estacionamento);
        Optional<TipoUsuario> optionalTipoUsuario = Optional.of(tipoUsuario);
        Optional<Usuario> optionalUsuario = Optional.empty();

        when(estacionamentoRepository.findById(anyLong())).thenReturn(estacionamentoOptional);
        when(tipoUsuarioRepository.findById(anyLong())).thenReturn(optionalTipoUsuario);
        when(usuarioRepository.findById(anyString())).thenReturn(optionalUsuario);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioFormDTO usuarioFormDTO = getUsuarioFormDTO();

        assertThrows(DomainException.class, () -> usuarioService.criar(usuarioFormDTO, 1L));
    }

    @Test
    void naoDeveriaCriarUsuarioQuandoOUsuarioJaExiste(){
        Usuario usuario = getUsuario();
        Estacionamento estacionamento = getEstacionamento();
        TipoUsuario tipoUsuario = getTipoUsuario();

        Optional<Estacionamento> estacionamentoOptional = Optional.of(estacionamento);
        Optional<TipoUsuario> optionalTipoUsuario = Optional.of(tipoUsuario);
        Optional<Usuario> optionalUsuario = Optional.of(usuario);

        when(estacionamentoRepository.findById(anyLong())).thenReturn(estacionamentoOptional);
        when(tipoUsuarioRepository.findById(anyLong())).thenReturn(optionalTipoUsuario);
        when(usuarioRepository.findById(anyString())).thenReturn(optionalUsuario);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioFormDTO usuarioFormDTO = getUsuarioFormDTO();

        assertThrows(DomainException.class, () -> usuarioService.criar(usuarioFormDTO, 1L));

    }

    @Test
    void naoDeveriaCriarUsuarioQuandoOTipoDeUsuarioNaoExiste(){
        Usuario usuario = getUsuario();
        Estacionamento estacionamento = getEstacionamento();

        Optional<Estacionamento> estacionamentoOptional = Optional.of(estacionamento);
        Optional<TipoUsuario> optionalTipoUsuario = Optional.empty();
        Optional<Usuario> optionalUsuario = Optional.empty();

        when(estacionamentoRepository.findById(anyLong())).thenReturn(estacionamentoOptional);
        when(tipoUsuarioRepository.findById(anyLong())).thenReturn(optionalTipoUsuario);
        when(usuarioRepository.findById(anyString())).thenReturn(optionalUsuario);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioFormDTO usuarioFormDTO = getUsuarioFormDTO();

        assertThrows(DomainNotFoundException.class, () -> usuarioService.criar(usuarioFormDTO, 1L));

    }



    @Test
    void deveriaAtualizarUsuario(){
        UsuarioFormUpdateDTO usuarioFormUpdateDTO = getUsuarioFormUpdateDTO();
        Usuario usuario = getUsuario();
        usuario.setNome(usuarioFormUpdateDTO.getNome());
        usuario.setSenha(usuarioFormUpdateDTO.getSenha());

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario); //retornar Usuario

        RespostaUsuarioDTO usuarioAtualizado = usuarioService.atualizar(getUsuarioFormUpdateDTO(), usuario);

        assertTrue(usuarioAtualizado.getNome() == usuarioFormUpdateDTO.getNome());
        assertTrue(usuarioAtualizado.getEmail() == usuario.getEmail());
    }

    private UsuarioFormDTO getUsuarioFormDTO() {
        UsuarioFormDTO usuarioFormDTO = new UsuarioFormDTO();
        usuarioFormDTO.setNome("Teste");
        usuarioFormDTO.setSenha("12345678");
        usuarioFormDTO.setEmail("teste@teste.com");
        usuarioFormDTO.setTipo_usuario_id(1L);
        return usuarioFormDTO;
    }

    private Usuario getUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNome("Teste");
        usuario.setSenha("12345678");
        usuario.setEmail("teste@teste.com");
        usuario.setEmpresa(getEmpresa());
        usuario.setEstacionamento(getEstacionamento());
        usuario.setTipos(getTipoUsuario());
        return usuario;
    }

    private Empresa getEmpresa(){
        Empresa empresa = new Empresa();
        empresa.setId(1L);
        empresa.setNome("Empresa Teste");
        empresa.setCnpj("19.814.412/0001-93");

        return empresa;
    }

    private Estacionamento getEstacionamento(){
        Estacionamento estacionamento = new Estacionamento();
        estacionamento.setId(1L);
        estacionamento.setNome("Empresa Teste");
        estacionamento.setEmpresa(getEmpresa());

        return estacionamento;
    }

    private TipoUsuario getTipoUsuario() {
        TipoUsuario tipoUsuario = new TipoUsuario();
        tipoUsuario.setId(1L);
        tipoUsuario.setNome("funcionario");

        return tipoUsuario;
    }
    private TipoUsuario getTipoUsuarioAdmin() {
        TipoUsuario tipoUsuario = new TipoUsuario();
        tipoUsuario.setId(2L);
        tipoUsuario.setNome("admin");

        return tipoUsuario;
    }

    @NotNull
    private UsuarioFormUpdateDTO getUsuarioFormUpdateDTO() {
        UsuarioFormUpdateDTO usuarioFormUpdateDTO = new UsuarioFormUpdateDTO();
        usuarioFormUpdateDTO.setNome("Teste usuário");
        usuarioFormUpdateDTO.setSenha("123456");
        return usuarioFormUpdateDTO;
    }

}