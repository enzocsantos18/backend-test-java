package br.com.estacionamento.service.empresa;

import br.com.estacionamento.config.exception.DomainException;
import br.com.estacionamento.config.exception.DomainNotFoundException;
import br.com.estacionamento.domain.dto.in.empresa.EmpresaFormDTO;
import br.com.estacionamento.domain.dto.in.empresa.EmpresaFormUpdateDTO;
import br.com.estacionamento.domain.dto.out.empresa.RespostaEmpresaDTO;
import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.empresa.Endereco;
import br.com.estacionamento.domain.empresa.Telefone;
import br.com.estacionamento.domain.usuario.TipoUsuario;
import br.com.estacionamento.domain.usuario.Usuario;
import br.com.estacionamento.repository.empresa.EmpresaRepository;
import br.com.estacionamento.repository.empresa.EnderecoRepository;
import br.com.estacionamento.repository.empresa.TelefoneRepository;
import br.com.estacionamento.repository.usuario.TipoUsuarioRepository;
import br.com.estacionamento.repository.usuario.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmpresaServiceTest {
    @InjectMocks
    private EmpresaService empresaService;
    @Mock
    private CepParaEnderecoService cepParaEnderecoService;
    @Mock
    private EmpresaRepository empresaRepository;
    @Mock
    private TelefoneRepository telefoneRepository;
    @Mock
    private EnderecoRepository enderecoRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Test
    void deveriaBuscarEmpresa() {
        Empresa empresa = getEmpresa();
        Optional<Empresa> empresaOptional = Optional.of(empresa);
        when(empresaRepository.findById(anyLong())).thenReturn(empresaOptional);

        RespostaEmpresaDTO respostaEmpresa = empresaService.buscar(1L);
        assertTrue(respostaEmpresa.getId() == empresa.getId());
        assertTrue(respostaEmpresa.getNome() == empresa.getNome());
        assertTrue(respostaEmpresa.getCnpj() == empresa.getCnpj());

    }

    @Test
    void naoDeveriaBuscarEmpresa() {
        Optional<Empresa> empresaOptionalVazio = Optional.empty();
        when(empresaRepository.findById(anyLong())).thenReturn(empresaOptionalVazio);
        assertThrows(DomainNotFoundException.class, () -> empresaService.buscar(1L));
    }

    @Test
    void deveriaCriarEmpresa() {
        EmpresaFormDTO empresaFormDTO = getEmpresaFormDTO();
        TipoUsuario tipoUsuario = getTipoUsuario();
        Telefone telefone = getTelefone();
        Empresa empresa = getEmpresa();
        Endereco endereco = getEndereco();
        Usuario usuario = getUsuario();

        Optional<Empresa> empresaOptionalVazio = Optional.empty();
        Optional<Usuario> usuarioOptionalVazio = Optional.empty();


        when(empresaRepository.findByCnpj(anyString())).thenReturn(empresaOptionalVazio);
        try {
            when(cepParaEnderecoService.buscarDadosEndereco(empresaFormDTO.getCep(), empresaFormDTO.getNumero()))
                    .thenReturn(endereco);
        } catch (Exception e) {
            fail();
        }
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco); //Endereco
        when(empresaRepository.save(any(Empresa.class))).thenReturn(empresa); //Empresa
        when(telefoneRepository.save(any(Telefone.class))).thenReturn(telefone); //Telefone
        when(tipoUsuarioRepository.findOneByNome("admin")).thenReturn(tipoUsuario); //Tipo Usuario
        when(usuarioRepository.findById(anyString())).thenReturn(usuarioOptionalVazio); //Usuario Vazio
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario); //Usuario

        RespostaEmpresaDTO respostaEmpresa = empresaService.criar(empresaFormDTO);

        assertTrue(respostaEmpresa.getNome() == empresa.getNome());
        assertTrue(respostaEmpresa.getId() == empresa.getId());
        assertTrue(respostaEmpresa.getCnpj() == empresa.getCnpj());
    }

    @Test
    void naoDeveriaCriarEmpresaQueTenhaUmCnpjIgualADeOutra() {
        EmpresaFormDTO empresaFormDTO = getEmpresaFormDTO();
        TipoUsuario tipoUsuario = getTipoUsuario();
        Telefone telefone = getTelefone();
        Empresa empresa = getEmpresa();
        Endereco endereco = getEndereco();
        Usuario usuario = getUsuario();

        Optional<Empresa> empresaOptional = Optional.of(empresa);
        Optional<Usuario> usuarioOptionalVazio = Optional.empty();


        when(empresaRepository.findByCnpj(anyString())).thenReturn(empresaOptional);
        try {
            when(cepParaEnderecoService.buscarDadosEndereco(empresaFormDTO.getCep(), empresaFormDTO.getNumero()))
                    .thenReturn(endereco);
        } catch (Exception e) {
            fail();
        }
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco); //Endereco
        when(empresaRepository.save(any(Empresa.class))).thenReturn(empresa); //Empresa
        when(telefoneRepository.save(any(Telefone.class))).thenReturn(telefone); //Telefone
        when(tipoUsuarioRepository.findOneByNome("admin")).thenReturn(tipoUsuario); //Tipo Usuario
        when(usuarioRepository.findById(anyString())).thenReturn(usuarioOptionalVazio); //Usuario Vazio
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario); //Usuario

        assertThrows(DomainException.class, () -> empresaService.criar(empresaFormDTO));
    }

    @Test
    void naoDeveriaCriarEmpresaQueOEmailDoUsuarioEstejaRepitido() {
        EmpresaFormDTO empresaFormDTO = getEmpresaFormDTO();
        TipoUsuario tipoUsuario = getTipoUsuario();
        Telefone telefone = getTelefone();
        Empresa empresa = getEmpresa();
        Endereco endereco = getEndereco();
        Usuario usuario = getUsuario();

        Optional<Empresa> empresaOptionalVazio = Optional.empty();
        Optional<Usuario> usuarioOptional = Optional.of(usuario);


        when(empresaRepository.findByCnpj(anyString())).thenReturn(empresaOptionalVazio);
        try {
            when(cepParaEnderecoService.buscarDadosEndereco(empresaFormDTO.getCep(), empresaFormDTO.getNumero()))
                    .thenReturn(endereco);
        } catch (Exception e) {
            fail();
        }
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco); //Endereco
        when(empresaRepository.save(any(Empresa.class))).thenReturn(empresa); //Empresa
        when(telefoneRepository.save(any(Telefone.class))).thenReturn(telefone); //Telefone
        when(tipoUsuarioRepository.findOneByNome("admin")).thenReturn(tipoUsuario); //Tipo Usuario
        when(usuarioRepository.findById(anyString())).thenReturn(usuarioOptional); //Usuario Vazio
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario); //Usuario

        assertThrows(DomainException.class, () -> empresaService.criar(empresaFormDTO));
    }

    @Test
    void deveriaAtualizarEmpresa() {
        EmpresaFormUpdateDTO empresaFormUpdateDTO = getEmpresaFormUpdateDTO();

        Empresa empresa = getEmpresa();
        Optional<Empresa> empresaOptional = Optional.of(empresa);
        Optional<Empresa> empresaOptionalVazio = Optional.empty();


        empresa.setNome("teste");
        empresa.setCnpj("55.571.039/0001-50");

        Endereco endereco = getEndereco();
        when(empresaRepository.findById(anyLong())).thenReturn(empresaOptional);
        when(empresaRepository.findByCnpj(anyString())).thenReturn(empresaOptionalVazio);
        when(empresaRepository.save(any(Empresa.class))).thenReturn(empresa); //EmpresaSalva
        try {
            when(cepParaEnderecoService.buscarDadosEndereco(empresaFormUpdateDTO.getCep(), empresaFormUpdateDTO.getNumero()))
                    .thenReturn(endereco);
        } catch (Exception e) {
            fail();
        }
        RespostaEmpresaDTO respostaEmpresaAtualizada = empresaService.atualizar(empresaFormUpdateDTO, 1L);

        assertTrue(respostaEmpresaAtualizada.getCnpj() == "55.571.039/0001-50");
        assertTrue(respostaEmpresaAtualizada.getEndereco().getNumero() == 202);
    }

    @Test
    void naoDeveriaAtualizarEmpresaQuandoEnderecoRetornarNulo() {
        EmpresaFormUpdateDTO empresaFormUpdateDTO = getEmpresaFormUpdateDTO();

        Empresa empresa = getEmpresa();
        Optional<Empresa> empresaOptional = Optional.of(empresa);
        Optional<Empresa> empresaOptionalVazio = Optional.empty();

        empresa.setNome("teste");
        empresa.setCnpj("55.571.039/0001-50");

        when(empresaRepository.findById(anyLong())).thenReturn(empresaOptional);
        when(empresaRepository.findByCnpj(anyString())).thenReturn(empresaOptionalVazio);
        when(empresaRepository.save(any(Empresa.class))).thenReturn(empresa);
        try {
            when(cepParaEnderecoService.buscarDadosEndereco(empresaFormUpdateDTO.getCep(), empresaFormUpdateDTO.getNumero()))
                    .thenReturn(null);
        } catch (Exception e) {
            fail();
        }
        assertThrows(DomainException.class, () -> empresaService.atualizar(empresaFormUpdateDTO, 1L));
    }

    @Test
    void naoDeveriaAtualizarEmpresaQuandoOutraEmpresaEstiverComOmesmoCNPJ() {
        EmpresaFormUpdateDTO empresaFormUpdateDTO = getEmpresaFormUpdateDTO();

        Empresa empresa = getEmpresa();
        Optional<Empresa> empresaOptional = Optional.of(empresa);

        empresa.setNome("teste");
        empresa.setCnpj("45.571.039/0001-50");

        Endereco endereco = getEndereco();

        when(empresaRepository.findById(anyLong())).thenReturn(empresaOptional);
        when(empresaRepository.findByCnpj(anyString())).thenReturn(empresaOptional);
        when(empresaRepository.save(any(Empresa.class))).thenReturn(empresa);
        try {
            when(cepParaEnderecoService.buscarDadosEndereco(empresaFormUpdateDTO.getCep(), empresaFormUpdateDTO.getNumero()))
                    .thenReturn(endereco);
        } catch (Exception e) {
            fail();
        }
        assertThrows(DomainException.class, () -> empresaService.atualizar(empresaFormUpdateDTO, 1L));

    }

    @Test
    void deveriaDeletarEmpresa() {
        Empresa empresa = getEmpresa();
        Optional<Empresa> empresaOptional = Optional.of(empresa);
        when(empresaRepository.findById(anyLong())).thenReturn(empresaOptional);
        doNothing().when(empresaRepository).deleteById(anyLong());

        empresaService.deletar(1L);

        verify(empresaRepository, times(1)).deleteById(anyLong());

    }

    @Test
    void naoDeveriaDeletarEmpresaCasoNaoExista() {
        Optional<Empresa> empresaOptionalVazio = Optional.empty();
        when(empresaRepository.findById(anyLong())).thenReturn(empresaOptionalVazio);

        assertThrows(DomainNotFoundException.class, () -> empresaService.deletar(1L));

    }

    @Test
    void deveriaGerarDomainExceptionValidarCnpj() {
        Empresa empresa = getEmpresa();
        Optional<Empresa> empresaOptional = Optional.of(empresa);
        when(empresaRepository.findByCnpj(anyString())).thenReturn(empresaOptional);

        assertThrows(DomainException.class, () -> empresaService.validarCnpj("19.814.412/0001-93"));
    }

    private Usuario getUsuario() {
        Usuario usuario = new Usuario();
        usuario.setEmpresa(getEmpresa());
        usuario.setSenha("123456");
        usuario.setNome("teste");
        usuario.setEmail("teste@teste.com");
        usuario.setTipos(getTipoUsuario());
        return usuario;
    }

    private TipoUsuario getTipoUsuario() {
        TipoUsuario tipoUsuario = new TipoUsuario();
        tipoUsuario.setId(1L);
        tipoUsuario.setNome("admin");
        return tipoUsuario;
    }

    private Telefone getTelefone() {
        Telefone telefone = new Telefone();
        telefone.setId(1L);
        telefone.setEmpresa(getEmpresa());
        telefone.setNumero("(13) 3465-2122");

        return telefone;
    }

    private EmpresaFormDTO getEmpresaFormDTO() {
        EmpresaFormDTO empresaFormDTO = new EmpresaFormDTO();
        empresaFormDTO.setNome("Teste");
        empresaFormDTO.setCnpj("55.571.039/0001-50");
        empresaFormDTO.setCep("11111-111");
        empresaFormDTO.setNumero(202);
        empresaFormDTO.setTelefone("(13) 3465-2122");
        empresaFormDTO.setNome_usuario("teste");
        empresaFormDTO.setEmail("teste@teste.com");
        empresaFormDTO.setSenha("123456");
        return empresaFormDTO;
    }

    private Endereco getEndereco() {
        Endereco endereco = new Endereco();
        endereco.setId(1L);
        endereco.setCep("11365-210");
        endereco.setLogradouro("teste");
        endereco.setBairro("teste");
        endereco.setCidade("teste");
        endereco.setEstado("teste");
        endereco.setNumero(202);
        return endereco;
    }

    private EmpresaFormUpdateDTO getEmpresaFormUpdateDTO() {
        EmpresaFormUpdateDTO empresaFormUpdateDTO = new EmpresaFormUpdateDTO();
        empresaFormUpdateDTO.setNome("teste");
        empresaFormUpdateDTO.setCnpj("55.571.039/0001-50");
        empresaFormUpdateDTO.setNumero(202);
        empresaFormUpdateDTO.setCep("11365-210");
        return empresaFormUpdateDTO;
    }

    private Empresa getEmpresa() {

        Endereco endereco = new Endereco();
        endereco.setId(1L);

        Empresa empresa = new Empresa();
        empresa.setId(1L);
        empresa.setNome("Empresa Teste");
        empresa.setCnpj("19.814.412/0001-93");
        empresa.setEndereco(endereco);


        return empresa;
    }

}