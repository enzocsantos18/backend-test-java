package br.com.estacionamento.repository;

import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.repository.empresa.EmpresaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class EmpresaRepositoryTest {
    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private TestEntityManager em;


    @Test
    void deveriaEncontrarEmpresaPeloCnpj() {

        String cnpj = "51.193.861/0001-74";

        Empresa empresa = new Empresa();
        empresa.setNome("teste");
        empresa.setCnpj(cnpj);

        em.persist(empresa);

        Optional<Empresa> empresaEncontrada = empresaRepository.findByCnpj(cnpj);
        assertTrue(empresaEncontrada.isPresent());

    }

    @Test
    void naoDeveriaEncontrarEmpresaPeloCnpj() {
        String cnpj = "teste";

        Optional<Empresa> empresaEncontrada = empresaRepository.findByCnpj(cnpj);
        assertFalse(empresaEncontrada.isPresent());
    }
}