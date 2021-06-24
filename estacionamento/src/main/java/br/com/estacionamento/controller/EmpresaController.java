package br.com.estacionamento.controller;

import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.dto.in.EmpresaFormDTO;
import br.com.estacionamento.service.empresa.EmpresaService;
import br.com.estacionamento.service.security.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private UserInformationService userInformationService;

    @GetMapping
    public ResponseEntity<Empresa> buscar(Authentication authentication) {
        Long empresaId = userInformationService.getEmpresaId(authentication);
        Empresa empresa = empresaService.buscarPorId(empresaId);
        return ResponseEntity.ok(empresa);
    }

    @PostMapping
    public ResponseEntity<Empresa> criar(@Valid @RequestBody EmpresaFormDTO dadosEmpresa, UriComponentsBuilder uriBuilder) {
        Empresa empresa = empresaService.criar(dadosEmpresa);
        URI uri = uriBuilder.path("/empresa/{id}").buildAndExpand(empresa.getId()).toUri();
        return ResponseEntity.created(uri).body(empresa);
    }

    @DeleteMapping
    public ResponseEntity deletar(Authentication authentication) {
        Long empresaId = userInformationService.getEmpresaId(authentication);
        empresaService.deletarPorId(empresaId);
        return ResponseEntity.ok().build();
    }

}
