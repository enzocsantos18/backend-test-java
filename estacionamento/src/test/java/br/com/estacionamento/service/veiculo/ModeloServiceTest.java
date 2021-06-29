package br.com.estacionamento.service.veiculo;

import br.com.estacionamento.domain.veiculo.Fabricante;
import br.com.estacionamento.domain.veiculo.Modelo;
import br.com.estacionamento.domain.veiculo.TipoVeiculo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ModeloServiceTest {
    @Mock
    private ModeloService modeloService;

    @Test
    void deveriaRetornarListaDeModelosDeDeterminadoFabricante() {
        Fabricante fabricante = new Fabricante();
        fabricante.setId(1L);
        fabricante.setNome("Fiat");

        Modelo modelo = new Modelo();
        modelo.setId(1L);
        modelo.setNome("Uno");
        modelo.setFabricante(fabricante);
        modelo.setTipoVeiculo(new TipoVeiculo());

        when(modeloService.listar(any(Long.class))).thenReturn(Arrays.asList(modelo, modelo, modelo));

        Long fabricanteId = 1L;

        List<Modelo> modelos = modeloService.listar(fabricanteId);

        assertTrue(modelos.get(0).getNome() == "Uno");
        assertTrue(modelos.get(0).getId() == 1L);

    }

    @Test
    void deveriaRetornarListaDeModelos() {
        Fabricante fabricante = new Fabricante();
        fabricante.setId(1L);
        fabricante.setNome("Fiat");

        Modelo modelo = new Modelo();
        modelo.setId(1L);
        modelo.setNome("Uno");
        modelo.setFabricante(fabricante);
        modelo.setTipoVeiculo(new TipoVeiculo());

        when(modeloService.listar(null)).thenReturn(Arrays.asList(modelo, modelo, modelo));

        List<Modelo> modelos = modeloService.listar(null);

        assertTrue(modelos.get(1).getNome() == "Uno");
        assertTrue(modelos.get(1).getId() == 1L);
    }

    @Test
    void deveriaRetornarListaDeModelosVaziaCasoNaoExistaOFabricanteFornecido() {
        when(modeloService.listar(999L)).thenReturn(Arrays.asList());

        List<Modelo> modelos = modeloService.listar(999L);

        assertTrue(modelos.size() == 0);
    }

    @Test
    void deveriaRetornarListaDeModelosVaziaCasoNaoExistaModelosCadastrados() {
        when(modeloService.listar(null)).thenReturn(Arrays.asList());

        List<Modelo> modelos = modeloService.listar(null);

        assertTrue(modelos.size() == 0);
    }

}