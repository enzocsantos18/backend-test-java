package br.com.estacionamento.controller.empresa;

import br.com.estacionamento.domain.dto.in.EmpresaFormUpdateDTO;
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
        Empresa empresa = empresaService.buscar(empresaId);
        return ResponseEntity.ok(empresa);
    }

    @PostMapping
    public ResponseEntity<Empresa> criar(@Valid @RequestBody EmpresaFormDTO dadosEmpresa) {
        Empresa empresa = empresaService.criar(dadosEmpresa);
        return ResponseEntity.status(201).body(empresa);
    }

    @PutMapping
    public ResponseEntity<Empresa> atualizar(@Valid @RequestBody EmpresaFormUpdateDTO dadosEmpresa, Authentication authentication) {
        Long empresaId = userInformationService.getEmpresaId(authentication);
        Empresa empresa = empresaService.atualizar(dadosEmpresa, empresaId);
        return ResponseEntity.ok(empresa);
    }

    @DeleteMapping
    public ResponseEntity deletar(Authentication authentication) {
        Long empresaId = userInformationService.getEmpresaId(authentication);
        empresaService.deletar(empresaId);
        return ResponseEntity.noContent().build();
    }
}
