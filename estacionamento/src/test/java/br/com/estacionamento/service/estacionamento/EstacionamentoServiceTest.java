package br.com.estacionamento.service.estacionamento;

import br.com.estacionamento.config.exception.DomainException;
import br.com.estacionamento.config.exception.DomainNotFoundException;
import br.com.estacionamento.domain.dto.in.estacionamento.EstacionamentoFormDTO;
import br.com.estacionamento.domain.dto.in.estacionamento.EstacionamentoFormUpdateDTO;
import br.com.estacionamento.domain.dto.out.estacionamento.RespostaEstacionamentoDTO;
import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.estacionamento.Estacionamento;
import br.com.estacionamento.domain.usuario.TipoUsuario;
import br.com.estacionamento.domain.usuario.Usuario;
import br.com.estacionamento.repository.empresa.EmpresaRepository;
import br.com.estacionamento.repository.estacionamento.EstacionamentoRepository;
import br.com.estacionamento.repository.usuario.TipoUsuarioRepository;
import br.com.estacionamento.repository.usuario.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class EstacionamentoServiceTest {

    @InjectMocks
    private EstacionamentoService estacionamentoService;

    @Mock
    private EstacionamentoRepository estacionamentoRepository;

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    void deveriaListarEstacionamentos() {
        Estacionamento estacionamento = getEstacionamento();
        List<Estacionamento> estacionamentos = Arrays.asList(estacionamento, estacionamento, estacionamento);

        when(estacionamentoRepository.findByEmpresaId(anyLong())).thenReturn(estacionamentos);
        List<RespostaEstacionamentoDTO> estacionamentosResposta = estacionamentoService.listar(1L);

        assertTrue(estacionamentosResposta.size() == 3);
        assertTrue(estacionamentosResposta.get(0).getId() == estacionamento.getId());
        assertTrue(estacionamentosResposta.get(1).getId() == estacionamento.getId());
        assertTrue(estacionamentosResposta.get(2).getId() == estacionamento.getId());
    }

    @Test
    void deveriaBuscarEstacionamento() {
        Estacionamento estacionamento = getEstacionamento();
        Optional<Estacionamento> estacionamentoOptional = Optional.of(estacionamento);

        when(estacionamentoRepository.findByEmpresaIdAndId(anyLong(), anyLong())).thenReturn(estacionamentoOptional);
        RespostaEstacionamentoDTO estacionamentoResposta = estacionamentoService.buscar(1L, 1L);

        assertTrue(estacionamentoResposta.getId() == 1L);
        assertTrue(estacionamentoResposta.getNome() == estacionamento.getNome());
    }

    @Test
    void naoDeveriaBuscarEstacionamentoQuandoEstacionamentoNaoExistir() {
        Optional<Estacionamento> estacionamentoOptional = Optional.empty();
        when(estacionamentoRepository.findByEmpresaIdAndId(anyLong(), anyLong())).thenReturn(estacionamentoOptional);
        assertThrows(DomainNotFoundException.class, () -> estacionamentoService.buscar(1L, 1L));
    }

    @Test
    void deveriaCriarEstacionamento() {
        Estacionamento estacionamento = getEstacionamento();
        Empresa empresa = getEmpresa();
        Usuario usuario = getUsuario();
        TipoUsuario tipoUsuario = getTipoUsuario();
        tipoUsuario.setNome("admin_estacionamento");

        EstacionamentoFormDTO estacionamentoFormDTO = getEstacionamentoFormDTO();


        Optional<Estacionamento> optionalEstacionamentoVazio = Optional.empty();
        Optional<Usuario> optionalUsuarioVazio = Optional.empty();
        Optional<Empresa> optionalEmpresa = Optional.of(empresa);


        when(empresaRepository.findById(anyLong())).thenReturn(optionalEmpresa); //Optional com empresa
        when(estacionamentoRepository.findByNomeAndEmpresaId(anyString(), anyLong())).thenReturn(optionalEstacionamentoVazio); //Optional Vazio estacionamento
        when(estacionamentoRepository.save(any(Estacionamento.class))).thenReturn(estacionamento); //Retorna estacionamento
        when(tipoUsuarioRepository.findOneByNome("admin_estacionamento")).thenReturn(tipoUsuario); //Retorna Tipo
        when(usuarioRepository.findById(anyString())).thenReturn(optionalUsuarioVazio); //Retorna usuario vazio
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario); //Retorna usuario

        RespostaEstacionamentoDTO estacionamentoResposta = estacionamentoService.criar(estacionamentoFormDTO, 1L);

        assertTrue(estacionamentoResposta.getNome() == estacionamentoFormDTO.getNome());
    }

    @Test
    void naoDeveriaCriarEstacionamentoQuandoJaExisteEstacionamentoComOMesmoNome() {
        Estacionamento estacionamento = getEstacionamento();
        Empresa empresa = getEmpresa();
        Usuario usuario = getUsuario();
        TipoUsuario tipoUsuario = getTipoUsuario();
        tipoUsuario.setNome("admin_estacionamento");

        EstacionamentoFormDTO estacionamentoFormDTO = getEstacionamentoFormDTO();

        Optional<Estacionamento> optionalEstacionamento = Optional.of(estacionamento);
        Optional<Usuario> optionalUsuarioVazio = Optional.empty();
        Optional<Empresa> optionalEmpresa = Optional.of(empresa);

        when(empresaRepository.findById(anyLong())).thenReturn(optionalEmpresa); //Optional com empresa
        when(estacionamentoRepository.findByNomeAndEmpresaId(anyString(), anyLong())).thenReturn(optionalEstacionamento); //Optional Vazio estacionamento
        when(estacionamentoRepository.save(any(Estacionamento.class))).thenReturn(estacionamento); //Retorna estacionamento
        when(tipoUsuarioRepository.findOneByNome("admin_estacionamento")).thenReturn(tipoUsuario); //Retorna Tipo
        when(usuarioRepository.findById(anyString())).thenReturn(optionalUsuarioVazio); //Retorna usuario vazio
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario); //Retorna usuario

        assertThrows(DomainException.class, () -> estacionamentoService.criar(estacionamentoFormDTO, 1L));
    }

    @Test
    void naoDeveriaCriarEstacionamentoQuandoUsuarioJaEstaCadastrado() {
        Estacionamento estacionamento = getEstacionamento();
        Empresa empresa = getEmpresa();
        Usuario usuario = getUsuario();
        TipoUsuario tipoUsuario = getTipoUsuario();
        tipoUsuario.setNome("admin_estacionamento");

        EstacionamentoFormDTO estacionamentoFormDTO = getEstacionamentoFormDTO();

        Optional<Estacionamento> optionalEstacionamentoVazio = Optional.empty();
        Optional<Usuario> optionalUsuarioVazio = Optional.of(usuario);
        Optional<Empresa> optionalEmpresa = Optional.of(empresa);

        when(empresaRepository.findById(anyLong())).thenReturn(optionalEmpresa); //Optional com empresa
        when(estacionamentoRepository.findByNomeAndEmpresaId(anyString(), anyLong())).thenReturn(optionalEstacionamentoVazio); //Optional Vazio estacionamento
        when(estacionamentoRepository.save(any(Estacionamento.class))).thenReturn(estacionamento); //Retorna estacionamento
        when(tipoUsuarioRepository.findOneByNome("admin_estacionamento")).thenReturn(tipoUsuario); //Retorna Tipo
        when(usuarioRepository.findById(anyString())).thenReturn(optionalUsuarioVazio); //Retorna usuario
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario); //Retorna usuario

        assertThrows(DomainException.class, () -> estacionamentoService.criar(estacionamentoFormDTO, 1L));
    }

    @Test
    void deveriaAtualizarEstacionamento() {
        Estacionamento estacionamento = getEstacionamento();
        Optional<Estacionamento> estacionamentoOptional = Optional.of(estacionamento);
        Optional<Estacionamento> estacionamentoOptionalVazio = Optional.empty();

        EstacionamentoFormUpdateDTO estacionamentoFormUpdateDTO = new EstacionamentoFormUpdateDTO();
        estacionamentoFormUpdateDTO.setNome("Teste");

        when(estacionamentoRepository.findByEmpresaIdAndId(anyLong(), anyLong())).thenReturn(estacionamentoOptional);
        when(estacionamentoRepository.findByNomeAndEmpresaId(anyString(), anyLong())).thenReturn(estacionamentoOptionalVazio);
        estacionamento.setNome("Teste");
        when(estacionamentoRepository.save(any(Estacionamento.class))).thenReturn(estacionamento);

        RespostaEstacionamentoDTO estacionamentoResposta = estacionamentoService.atualizar(1L, 1L, estacionamentoFormUpdateDTO);

        assertTrue(estacionamentoResposta.getNome() == "Teste");
        assertTrue(estacionamentoResposta.getId() == 1L);
    }

    @Test
    void naoDeveriaAtualizarEstacionamentoQuandoExistirEstacionamentoComOMesmoNome() {
        Estacionamento estacionamento = getEstacionamento();
        Optional<Estacionamento> estacionamentoOptional = Optional.of(estacionamento);

        EstacionamentoFormUpdateDTO estacionamentoFormUpdateDTO = new EstacionamentoFormUpdateDTO();
        estacionamentoFormUpdateDTO.setNome("Teste");

        when(estacionamentoRepository.findByEmpresaIdAndId(anyLong(), anyLong())).thenReturn(estacionamentoOptional);
        when(estacionamentoRepository.findByNomeAndEmpresaId(estacionamentoFormUpdateDTO.getNome(), 1L)).thenReturn(estacionamentoOptional);
        estacionamento.setNome("Teste");
        when(estacionamentoRepository.save(any(Estacionamento.class))).thenReturn(estacionamento);

        assertThrows(DomainException.class, () -> estacionamentoService.atualizar(1L, 1L, estacionamentoFormUpdateDTO));

    }

    @Test
    void deveriaGerarDomainExceptionVerificaDisponibilidadeEmail() {
        Usuario usuario = getUsuario();
        Optional<Usuario> optionalUsuario = Optional.of(usuario);
        when(usuarioRepository.findById(anyString())).thenReturn(optionalUsuario);

        assertThrows(DomainException.class, () -> estacionamentoService.verificaDisponibilidadeEmail("teste@gmail.com"));
    }

    @Test
    void deveriaGerarDomainExceptionVerificaDisponibilidadeNome() {
        Estacionamento estacionamento = getEstacionamento();
        Optional<Estacionamento> optionalEstacionamento = Optional.of(estacionamento);
        when(estacionamentoRepository.findByNomeAndEmpresaId(anyString(), anyLong())).thenReturn(optionalEstacionamento);

        assertThrows(DomainException.class, () -> estacionamentoService.verificaDisponibilidadeNome(1L, "Estacionamento Teste"));
    }

    private Empresa getEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setId(1L);
        empresa.setNome("Empresa Teste");
        empresa.setCnpj("19.814.412/0001-93");

        return empresa;
    }

    private Estacionamento getEstacionamento() {
        Estacionamento estacionamento = new Estacionamento();
        estacionamento.setId(1L);
        estacionamento.setNome("Estacionamento Teste");
        estacionamento.setEmpresa(getEmpresa());

        return estacionamento;
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

    private TipoUsuario getTipoUsuario() {
        TipoUsuario tipoUsuario = new TipoUsuario();
        tipoUsuario.setId(1L);
        tipoUsuario.setNome("funcionario");

        return tipoUsuario;
    }

    private EstacionamentoFormDTO getEstacionamentoFormDTO() {
        EstacionamentoFormDTO estacionamentoFormDTO = new EstacionamentoFormDTO();
        estacionamentoFormDTO.setNome("Estacionamento Teste");
        estacionamentoFormDTO.setEmail("teste@teste.com");
        estacionamentoFormDTO.setNome_usuario("Teste");
        estacionamentoFormDTO.setSenha("12345678");
        return estacionamentoFormDTO;
    }
}