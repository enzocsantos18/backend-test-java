package br.com.estacionamento.controller;

import br.com.estacionamento.domain.dto.in.EmpresaFormDTO;
import br.com.estacionamento.domain.dto.in.EmpresaFormUpdateDTO;
import br.com.estacionamento.domain.dto.in.TelefoneFormDTO;
import br.com.estacionamento.domain.empresa.Empresa;
import br.com.estacionamento.domain.empresa.Telefone;
import br.com.estacionamento.service.empresa.EmpresaService;
import br.com.estacionamento.service.empresa.TelefoneService;
import br.com.estacionamento.service.security.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/empresa/telefone")
public class TelefoneController {

    @Autowired
    private UserInformationService userInformationService;
    @Autowired
    private TelefoneService telefoneService;

    @PostMapping
    public ResponseEntity<Telefone> adicionar(@RequestBody @Valid TelefoneFormDTO dadosTelefone, Authentication authentication) {
        Long empresaId = userInformationService.getEmpresaId(authentication);
        Telefone telefone = telefoneService.criar(dadosTelefone, empresaId);
        return ResponseEntity.status(201).body(telefone);
    }

    @DeleteMapping
    public ResponseEntity deletar(@RequestBody @Valid TelefoneFormDTO dadosTelefone, Authentication authentication) {
        Long empresaId = userInformationService.getEmpresaId(authentication);
        telefoneService.deletar(dadosTelefone,empresaId);
        return ResponseEntity.ok().build();
    }

}
