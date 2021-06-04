package br.com.estacionamento.service;

import br.com.estacionamento.domain.Empresa;
import br.com.estacionamento.domain.dto.in.EmpresaFormDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest

class EmpresaServiceTest {

    @Autowired
    private EmpresaService empresaService;
    @Test
    void deveriaCriarEmpresaService() {
        EmpresaFormDTO empresaFormDTO = new EmpresaFormDTO();
        empresaFormDTO.setCep("11365-210");
        empresaFormDTO.setCnpj("51.193.861/0001-74");
        empresaFormDTO.setNome("teste");
        empresaFormDTO.setTelefone("(13) 3462-1234");
        empresaFormDTO.setNumero(202);

        Empresa empresa = empresaService.criar(empresaFormDTO);

        assertEquals(empresa.getEndereco().getCidade(), "São Vicente");
        assertEquals(empresa.getTelefone().get(0).getNumero(), "(13) 3462-1234");
        assertEquals(empresa.getCnpj(), "51.193.861/0001-74");
        assertTrue(empresa.getId() > 0);

    }
}