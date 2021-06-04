package br.com.estacionamento.service;

import br.com.estacionamento.domain.Endereco;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class CepParaEnderecoServiceTest {

    @Autowired
    private CepParaEnderecoService cepParaEnderecoService;

    @Test
    void buscarDadosEndereco() {
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