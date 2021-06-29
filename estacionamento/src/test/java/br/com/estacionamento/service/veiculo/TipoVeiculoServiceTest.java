package br.com.estacionamento.service.veiculo;

import br.com.estacionamento.domain.veiculo.TipoVeiculo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class TipoVeiculoServiceTest {
    @Mock
    private TipoVeiculoService tipoVeiculoService;

    @Test
    void deveriaListarTiposDeVeiculos() {
        TipoVeiculo tipoVeiculo = new TipoVeiculo();
        tipoVeiculo.setId(1L);
        tipoVeiculo.setTipo("Carro");
        when(tipoVeiculoService.listar()).thenReturn(Arrays.asList(tipoVeiculo, tipoVeiculo, tipoVeiculo));
        List<TipoVeiculo> tipoVeiculos = tipoVeiculoService.listar();

        assertTrue(tipoVeiculos.get(1).getId() == 1L);
        assertTrue(tipoVeiculos.get(1).getTipo() == "Carro");
        assertTrue(tipoVeiculos.size() == 3);
    }

    @Test
    void deveriaRetornarListaDeTiposDeVeiculosVazia() {
        when(tipoVeiculoService.listar()).thenReturn(Arrays.asList());
        List<TipoVeiculo> tipoVeiculos = tipoVeiculoService.listar();
        assertTrue(tipoVeiculos.size() == 0);
    }
}