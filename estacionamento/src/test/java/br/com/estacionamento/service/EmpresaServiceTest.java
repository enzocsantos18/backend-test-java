package br.com.estacionamento.service;

import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.dto.in.empresa.EmpresaFormDTO;
import br.com.estacionamento.service.empresa.EmpresaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class EmpresaServiceTest {
//
//    @Autowired
//    private EmpresaService empresaService;
//    @Test
//    void deveriaCriarEmpresaService() {
//        EmpresaFormDTO empresaFormDTO = new EmpresaFormDTO();
//        empresaFormDTO.setCep("11365-210");
//        empresaFormDTO.setCnpj("51.193.861/0001-74");
//        empresaFormDTO.setNome("teste");
//        empresaFormDTO.setTelefone("(13) 3462-1234");
//        empresaFormDTO.setNumero(202);
//
//        Empresa empresa = empresaService.criar(empresaFormDTO);
//
//        assertEquals(empresa.getEndereco().getCidade(), "São Vicente");
//        assertEquals(empresa.getTelefone().get(0).getNumero(), "(13) 3462-1234");
//        assertEquals(empresa.getCnpj(), "51.193.861/0001-74");
//        assertTrue(empresa.getId() > 0);
//
//    }
//
//    @Test
//    void deveriaRetornarEmpresaBuscaPorId(){
//        Long id = 1L;
//        EmpresaFormDTO empresaFormDTO = new EmpresaFormDTO();
//        empresaFormDTO.setCep("11365-210");
//        empresaFormDTO.setCnpj("51.193.861/0001-74");
//        empresaFormDTO.setNome("teste");
//        empresaFormDTO.setTelefone("(13) 3462-1234");
//        empresaFormDTO.setNumero(202);
//
//        Empresa empresaCriada = empresaService.criar(empresaFormDTO);
//
//
//        Empresa empresa = empresaService.buscar(id);
//
//        assertEquals(empresa.getNome(), empresaCriada.getNome());
//        assertEquals(empresa.getCnpj(), empresaCriada.getCnpj());
//    }
//
//    @Test
//    void deveriaDeletarEmpresa(){
//        Long id = 1L;
//        EmpresaFormDTO empresaFormDTO = new EmpresaFormDTO();
//        empresaFormDTO.setCep("11365-210");
//        empresaFormDTO.setCnpj("51.193.861/0001-74");
//        empresaFormDTO.setNome("teste");
//        empresaFormDTO.setTelefone("(13) 3462-1234");
//        empresaFormDTO.setNumero(202);
//
//        Empresa empresaCriada = empresaService.criar(empresaFormDTO);
//
//        empresaService.deletar(empresaCriada.getId());
//
//        assertThrows(RuntimeException.class, () -> empresaService.buscar(1L));
//    }
}