package br.com.estacionamento.service;

import br.com.estacionamento.domain.empresa.Endereco;
import br.com.estacionamento.service.empresa.CepParaEnderecoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
class CepParaEnderecoServiceTest {

    @Autowired
    private CepParaEnderecoService cepParaEnderecoService;

    @Test
    void deveriaEncontrarDadosDeEndereco() {
        try {
            Endereco enderecoBusca = cepParaEnderecoService.buscarDadosEndereco("01001-000", 202);
            assertEquals(enderecoBusca.getCep(), "01001-000");
            assertEquals(enderecoBusca.getLogradouro(), "Praça da Sé");
            assertEquals(enderecoBusca.getNumero(), 202);
            assertEquals(enderecoBusca.getBairro(), "Sé");
            assertEquals(enderecoBusca.getCidade(), "São Paulo");
            assertEquals(enderecoBusca.getEstado(), "SP");
        } catch (Exception e) {
            fail("Erro ao gerar endereço");
        }
    }
}