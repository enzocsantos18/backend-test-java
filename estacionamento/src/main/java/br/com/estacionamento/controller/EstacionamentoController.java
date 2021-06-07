package br.com.estacionamento.controller;

import br.com.estacionamento.domain.Empresa;
import br.com.estacionamento.domain.Estacionamento;
import br.com.estacionamento.domain.dto.in.EmpresaFormDTO;
import br.com.estacionamento.domain.dto.in.EstacionamentoFormDTO;
import br.com.estacionamento.service.EmpresaService;
import br.com.estacionamento.service.EstacionamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/estacionamento")
public class EstacionamentoController {

    @Autowired
    private EstacionamentoService estacionamentoService;



    @PostMapping
    public ResponseEntity<Estacionamento> criar(@RequestBody @Valid EstacionamentoFormDTO dadosEstacionamento, UriComponentsBuilder uriBuilder){
        try {
            Estacionamento estacionamento = estacionamentoService.criar(dadosEstacionamento);
            URI uri = uriBuilder.path("/estacionamento/{estacionamento}/{id}").buildAndExpand(dadosEstacionamento.getId_empresa(),estacionamento.getId()).toUri();
            return ResponseEntity.created(uri).body(estacionamento);
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
