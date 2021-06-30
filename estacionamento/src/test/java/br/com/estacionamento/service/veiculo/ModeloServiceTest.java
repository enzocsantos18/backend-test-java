package br.com.estacionamento.service.veiculo;

import br.com.estacionamento.domain.veiculo.Fabricante;
import br.com.estacionamento.domain.veiculo.Modelo;
import br.com.estacionamento.domain.veiculo.TipoVeiculo;
import br.com.estacionamento.repository.veiculo.ModeloRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class ModeloServiceTest {
    @InjectMocks
    private ModeloService modeloService;

    @Mock
    private ModeloRepository modeloRepository;

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

        when(modeloRepository.findAll()).thenReturn(Arrays.asList(modelo, modelo, modelo));
        when(modeloRepository.findAllByFabricanteId(anyLong())).thenReturn(Arrays.asList(modelo));

        List<Modelo> modelos = modeloService.listar(null);
        List<Modelo> modelosFabricante = modeloService.listar(1L);

        assertTrue(modelos.get(1).getNome() == "Uno");
        assertTrue(modelos.get(1).getId() == 1L);

        assertTrue(modelosFabricante.size() == 1);
    }

    @Test
    void deveriaRetornarListaDeModelosVaziaCasoNaoExistaOFabricanteFornecido() {
        when(modeloRepository.findAllByFabricanteId(999L)).thenReturn(Arrays.asList());

        List<Modelo> modelos = modeloService.listar(999L);

        assertTrue(modelos.size() == 0);
    }

    @Test
    void deveriaRetornarListaDeModelosVaziaCasoNaoExistaModelosCadastrados() {
        when(modeloRepository.findAllByFabricanteId(anyLong())).thenReturn(Arrays.asList());

        List<Modelo> modelos = modeloService.listar(null);

        assertTrue(modelos.size() == 0);
    }

}