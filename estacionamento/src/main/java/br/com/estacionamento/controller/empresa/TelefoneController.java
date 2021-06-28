package br.com.estacionamento.controller.empresa;

import br.com.estacionamento.domain.dto.in.empresa.TelefoneFormDTO;
import br.com.estacionamento.domain.dto.out.empresa.RespostaTelefoneDTO;
import br.com.estacionamento.domain.empresa.Telefone;
import br.com.estacionamento.service.empresa.TelefoneService;
import br.com.estacionamento.service.security.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/empresa/telefone")
public class TelefoneController {
    @Autowired
    private UserInformationService userInformationService;
    @Autowired
    private TelefoneService telefoneService;

    @PostMapping
    public ResponseEntity<RespostaTelefoneDTO> criar(@RequestBody @Valid TelefoneFormDTO dadosTelefone, Authentication authentication) {
        Long empresaId = userInformationService.getEmpresaId(authentication);
        RespostaTelefoneDTO telefone = telefoneService.criar(dadosTelefone, empresaId);
        return ResponseEntity.status(201).body(telefone);
    }

    @DeleteMapping
    public ResponseEntity deletar(@RequestBody @Valid TelefoneFormDTO dadosTelefone, Authentication authentication) {
        Long empresaId = userInformationService.getEmpresaId(authentication);
        telefoneService.deletar(dadosTelefone, empresaId);
        return ResponseEntity.noContent().build();
    }
}
