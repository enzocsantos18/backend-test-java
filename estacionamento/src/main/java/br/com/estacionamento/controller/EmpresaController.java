package br.com.estacionamento.controller;

import br.com.estacionamento.domain.Empresa;
import br.com.estacionamento.domain.dto.in.EmpresaFormDTO;
import br.com.estacionamento.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> buscar(@PathVariable("id") Long id) {
        Empresa empresa = empresaService.buscarPorId(id);
        return ResponseEntity.ok(empresa);
    }

    @PostMapping
    public ResponseEntity<Empresa> criar(@Valid @RequestBody EmpresaFormDTO dadosEmpresa, UriComponentsBuilder uriBuilder) {
        Empresa empresa = empresaService.criar(dadosEmpresa);
        URI uri = uriBuilder.path("/empresa/{id}").buildAndExpand(empresa.getId()).toUri();
        return ResponseEntity.created(uri).body(empresa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable("id") Long id) {
        empresaService.deletarPorId(id);
        return ResponseEntity.ok().build();
    }

}
