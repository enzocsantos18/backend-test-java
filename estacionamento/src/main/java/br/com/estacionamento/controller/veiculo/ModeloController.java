package br.com.estacionamento.controller.veiculo;

import br.com.estacionamento.domain.veiculo.Modelo;
import br.com.estacionamento.service.veiculo.ModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/modelo")
public class ModeloController {
    @Autowired
    private ModeloService modeloService;

    @GetMapping
    public ResponseEntity<List<Modelo>> listar(@RequestParam(value = "fabricanteId", required = false) Long fabricanteId) {
        List<Modelo> modelos = modeloService.listar(fabricanteId);
        return ResponseEntity.ok(modelos);
    }


}
