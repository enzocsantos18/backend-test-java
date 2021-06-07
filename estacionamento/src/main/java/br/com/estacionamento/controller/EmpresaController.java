package br.com.estacionamento.controller;

import br.com.estacionamento.domain.Empresa;
import br.com.estacionamento.domain.Endereco;
import br.com.estacionamento.domain.dto.in.EmpresaFormDTO;
import br.com.estacionamento.domain.dto.in.EmpresaFormUpdateDTO;
import br.com.estacionamento.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
    public ResponseEntity<Empresa> buscarEmpresaPeloId(@PathVariable("id") Long id){
        try {

            Empresa empresa = empresaService.buscarPorId(id);
            return ResponseEntity.ok(empresa);
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Empresa> criarEmpresa(@RequestBody @Valid  EmpresaFormDTO dadosEmpresa, UriComponentsBuilder uriBuilder){
        try {
            Empresa empresa = empresaService.criar(dadosEmpresa);
            URI uri = uriBuilder.path("/empresa/{id}").buildAndExpand(empresa.getId()).toUri();
            return ResponseEntity.created(uri).body(empresa);
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarEmpresa(@PathVariable("id") Long id){
        try{
            empresaService.deletarPorId(id);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}
