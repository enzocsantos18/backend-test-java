package br.com.estacionamento.service.veiculo;

import br.com.estacionamento.domain.veiculo.Fabricante;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


@SpringBootTest
class FabricanteServiceTest {
    @Mock
    private FabricanteService fabricanteService;

    @Test
    void deveriaListarFabricantes() {
        Fabricante fabricante = new Fabricante();
        fabricante.setId(1L);
        fabricante.setNome("Honda");
        when(fabricanteService.listar()).thenReturn(Arrays.asList(fabricante, fabricante, fabricante));
        List<Fabricante> fabricantes = fabricanteService.listar();

        assertTrue(fabricantes.get(1).getId() == 1L);
        assertTrue(fabricantes.get(1).getNome() == "Honda");
        assertTrue(fabricantes.size() == 3);
    }

    @Test
    void deveriaRetornarListaDeFabricantesVazia() {
        when(fabricanteService.listar()).thenReturn(Arrays.asList());
        List<Fabricante> fabricantes = fabricanteService.listar();

        assertTrue(fabricantes.size() == 0);
    }
}