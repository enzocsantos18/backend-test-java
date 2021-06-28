package br.com.estacionamento.controller.empresa;

import br.com.estacionamento.domain.dto.in.empresa.EmpresaFormUpdateDTO;
import br.com.estacionamento.domain.dto.out.empresa.RespostaEmpresaDTO;
import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.dto.in.empresa.EmpresaFormDTO;
import br.com.estacionamento.service.empresa.EmpresaService;
import br.com.estacionamento.service.security.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {
    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private UserInformationService userInformationService;


    @GetMapping
    public ResponseEntity<RespostaEmpresaDTO> buscar(Authentication authentication) {
        Long empresaId = userInformationService.getEmpresaId(authentication);
        RespostaEmpresaDTO empresa = empresaService.buscar(empresaId);
        return ResponseEntity.ok(empresa);
    }

    @PostMapping
    public ResponseEntity<RespostaEmpresaDTO> criar(@Valid @RequestBody EmpresaFormDTO dadosEmpresa) {
        RespostaEmpresaDTO empresa = empresaService.criar(dadosEmpresa);
        return ResponseEntity.status(201).body(empresa);
    }

    @PutMapping
    public ResponseEntity<RespostaEmpresaDTO> atualizar(@Valid @RequestBody EmpresaFormUpdateDTO dadosEmpresa, Authentication authentication) {
        Long empresaId = userInformationService.getEmpresaId(authentication);
        RespostaEmpresaDTO empresa = empresaService.atualizar(dadosEmpresa, empresaId);
        return ResponseEntity.ok(empresa);
    }

    @DeleteMapping
    public ResponseEntity deletar(Authentication authentication) {
        Long empresaId = userInformationService.getEmpresaId(authentication);
        empresaService.deletar(empresaId);
        return ResponseEntity.noContent().build();
    }
}
