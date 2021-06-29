package br.com.estacionamento.service.empresa;

import br.com.estacionamento.config.exception.DomainException;
import br.com.estacionamento.config.exception.DomainNotFoundException;
import br.com.estacionamento.domain.dto.in.empresa.TelefoneFormDTO;
import br.com.estacionamento.domain.dto.out.empresa.RespostaTelefoneDTO;
import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.empresa.Telefone;
import br.com.estacionamento.repository.empresa.TelefoneRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TelefoneServiceTest {

    @Mock
    private TelefoneService telefoneService;

    @Mock
    private TelefoneRepository telefoneRepository;

    @Test
    void deveriaCriarNovoTelefone(){
        String numero = "(13) 3333-3333";
        TelefoneFormDTO telefoneFormDTO = getTelefoneFormDTO(numero);


        Telefone telefone = telefoneFormDTO.converterParaTelefone(new Empresa());
        RespostaTelefoneDTO respostaTelefoneDTO = new RespostaTelefoneDTO(telefone);

        when(telefoneService.criar(any(TelefoneFormDTO.class), any(Long.class))).thenReturn(respostaTelefoneDTO);


        RespostaTelefoneDTO telefoneCriado = telefoneService.criar(telefoneFormDTO, 1L);

        assertTrue(telefoneFormDTO.getNumero() == numero);
    }


    @Test
    void naoDeveriaCriarNovoTelefoneCasoEleJaEstejaCadastrado(){
        String numero = "(13) 3333-3333";
        TelefoneFormDTO telefoneFormDTO = new TelefoneFormDTO();
        telefoneFormDTO.setNumero(numero);

        when(telefoneService.criar(telefoneFormDTO, 1L)).thenThrow(DomainException.class);

        assertThrows(DomainException.class, () -> telefoneService.criar(telefoneFormDTO, 1L));

    }

    @Test
    void deveriaDeletarTelefone(){
        String numero = "(13) 3333-3333";

        TelefoneFormDTO telefoneFormDTO = getTelefoneFormDTO(numero);

        assertDoesNotThrow(() -> telefoneService.deletar(telefoneFormDTO,1L));
    }

    @Test
    void naoDeveriaDeletarTelefoneCasoEleNaoEstejaCadastrado(){
        String numero = "(13) 3333-3333";

        TelefoneFormDTO telefoneFormDTO = getTelefoneFormDTO(numero);
        doThrow(new DomainNotFoundException("Telefone não encontrado")).when(telefoneService).deletar(telefoneFormDTO, 1L);

        assertThrows(DomainNotFoundException.class,() -> telefoneService.deletar(telefoneFormDTO,1L));
    }


    @NotNull
    private TelefoneFormDTO getTelefoneFormDTO(String numero) {
        TelefoneFormDTO telefoneFormDTO = new TelefoneFormDTO();
        telefoneFormDTO.setNumero(numero);
        return telefoneFormDTO;
    }
}