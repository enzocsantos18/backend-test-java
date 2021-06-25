package br.com.estacionamento.controller;

import br.com.estacionamento.domain.veiculo.Fabricante;
import br.com.estacionamento.service.veiculo.FabricanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/fabricante")
public class FabricanteController {

    @Autowired
    private FabricanteService fabricanteService;

    @GetMapping
    public ResponseEntity<List<Fabricante>> listar() {
        List<Fabricante> fabricantes = fabricanteService.listar();
        return ResponseEntity.ok(fabricantes);
    }


}
