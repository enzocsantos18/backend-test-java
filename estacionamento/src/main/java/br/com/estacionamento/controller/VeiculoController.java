package br.com.estacionamento.controller;

import br.com.estacionamento.domain.Empresa;
import br.com.estacionamento.domain.Veiculo;
import br.com.estacionamento.domain.dto.in.EmpresaFormDTO;
import br.com.estacionamento.domain.dto.in.VeiculoFormDTO;
import br.com.estacionamento.service.EmpresaService;
import br.com.estacionamento.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @GetMapping("/{estacionamento}/{placa}")
    public ResponseEntity<Veiculo> bucar(@PathVariable("placa") String placa, @PathVariable("estacionamento") Long estacionamento){
        try {

            Veiculo veiculo = veiculoService.buscarPelaPlaca(placa, estacionamento);
            return ResponseEntity.ok(veiculo);
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Veiculo> criarVeiculo(@RequestBody @Valid VeiculoFormDTO dadosVeiculo, UriComponentsBuilder uriBuilder){
        try {
            Veiculo veiculo = veiculoService.criar(dadosVeiculo);
            URI uri = uriBuilder.path("/veiculo/{id}").buildAndExpand(veiculo.getId()).toUri();
            return ResponseEntity.created(uri).body(veiculo);
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
