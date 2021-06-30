package br.com.estacionamento.service.empresa;

import br.com.estacionamento.config.exception.DomainException;
import br.com.estacionamento.config.exception.DomainNotFoundException;
import br.com.estacionamento.domain.dto.in.empresa.TelefoneFormDTO;
import br.com.estacionamento.domain.dto.out.empresa.RespostaTelefoneDTO;
import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.empresa.Endereco;
import br.com.estacionamento.domain.empresa.Telefone;
import br.com.estacionamento.repository.empresa.EmpresaRepository;
import br.com.estacionamento.repository.empresa.TelefoneRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class TelefoneServiceTest {

    @InjectMocks
    private TelefoneService telefoneService;

    @Mock
    private TelefoneRepository telefoneRepository;

    @Mock
    private EmpresaRepository empresaRepository;

    @Test
    void deveriaCriarNovoTelefone() {
        String numero = "(13) 3465-2122";

        Empresa empresa = getEmpresa();
        Telefone telefone = getTelefone();
        TelefoneFormDTO telefoneFormDTO = getTelefoneFormDTO(numero);

        Optional<Empresa> empresaOptional = Optional.of(empresa);
        Optional<Telefone> optionalTelefoneVazio = Optional.empty();

        when(telefoneRepository.findByNumeroAndEmpresaId(anyString(), anyLong())).thenReturn(optionalTelefoneVazio);
        when(empresaRepository.findById(anyLong())).thenReturn(empresaOptional);//Optional Empresa Cheio
        when(telefoneRepository.save(any(Telefone.class))).thenReturn(telefone); //telefone

        RespostaTelefoneDTO telefoneCriado = telefoneService.criar(telefoneFormDTO, 1L);

        assertTrue(telefoneCriado.getNumero() == numero);
    }


    @Test
    void naoDeveriaCriarNovoTelefoneCasoEleJaEstejaCadastrado() {
        String numero = "(13) 3465-2122";

        Empresa empresa = getEmpresa();
        Telefone telefone = getTelefone();
        TelefoneFormDTO telefoneFormDTO = getTelefoneFormDTO(numero);

        Optional<Empresa> empresaOptional = Optional.of(empresa);
        Optional<Telefone> optionalTelefone = Optional.of(telefone);

        when(telefoneRepository.findByNumeroAndEmpresaId(anyString(), anyLong())).thenReturn(optionalTelefone);
        when(empresaRepository.findById(anyLong())).thenReturn(empresaOptional);//Optional Empresa Cheio
        when(telefoneRepository.save(any(Telefone.class))).thenReturn(telefone); //telefone

        assertThrows(DomainException.class, () -> telefoneService.criar(telefoneFormDTO, 1L));

    }

    @Test
    void deveriaDeletarTelefone() {
        Telefone telefone = getTelefone();
        Optional<Telefone> optionalTelefone = Optional.of(telefone);
        TelefoneFormDTO telefoneFormDTO = getTelefoneFormDTO(telefone.getNumero());

        when(telefoneRepository.findByNumeroAndEmpresaId(anyString(), anyLong())).thenReturn(optionalTelefone);

        doNothing().when(telefoneRepository).deleteById(anyLong());

        telefoneService.deletar(telefoneFormDTO, 1L);

        verify(telefoneRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void naoDeveriaDeletarTelefoneCasoEleNaoEstejaCadastrado() {
        Telefone telefone = getTelefone();
        Optional<Telefone> optionalTelefoneVaio = Optional.empty();
        TelefoneFormDTO telefoneFormDTO = getTelefoneFormDTO(telefone.getNumero());

        when(telefoneRepository.findByNumeroAndEmpresaId(anyString(), anyLong())).thenReturn(optionalTelefoneVaio);

        assertThrows(DomainNotFoundException.class, () -> telefoneService.deletar(telefoneFormDTO, 1L));
    }


    @NotNull
    private TelefoneFormDTO getTelefoneFormDTO(String numero) {
        TelefoneFormDTO telefoneFormDTO = new TelefoneFormDTO();
        telefoneFormDTO.setNumero(numero);
        return telefoneFormDTO;
    }

    private Telefone getTelefone() {
        Telefone telefone = new Telefone();
        telefone.setId(1L);
        telefone.setEmpresa(getEmpresa());
        telefone.setNumero("(13) 3465-2122");

        return telefone;
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