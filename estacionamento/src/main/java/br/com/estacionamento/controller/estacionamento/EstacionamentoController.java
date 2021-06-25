package br.com.estacionamento.controller.estacionamento;

import br.com.estacionamento.domain.estacionamento.Estacionamento;
import br.com.estacionamento.domain.dto.in.EstacionamentoFormDTO;
import br.com.estacionamento.domain.dto.in.EstacionamentoFormUpdateDTO;
import br.com.estacionamento.service.estacionamento.EstacionamentoService;
import br.com.estacionamento.service.security.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/estacionamento")
public class EstacionamentoController {
    @Autowired
    private EstacionamentoService estacionamentoService;

    @Autowired
    private UserInformationService userInformationService;

    @GetMapping
    public ResponseEntity<List<Estacionamento>> listar(Authentication authentication) {
        Long empresaId = userInformationService.getEmpresaId(authentication);
        List<Estacionamento> listagem = estacionamentoService.listar(empresaId);
        return ResponseEntity.ok(listagem);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estacionamento> buscar(@PathVariable("id") Long estacionamentoId, Authentication authentication) {
        Long empresaId = userInformationService.getEmpresaId(authentication);
        Estacionamento estacionamento = estacionamentoService.buscar(empresaId, estacionamentoId);
        return ResponseEntity.ok(estacionamento);
    }

    @PostMapping
    public ResponseEntity<Estacionamento> criar(@RequestBody @Valid EstacionamentoFormDTO dadosEstacionamento,
                                                Authentication authentication,
                                                UriComponentsBuilder uriBuilder) {
        Long empresaId = userInformationService.getEmpresaId(authentication);
        Estacionamento estacionamento = estacionamentoService.criar(dadosEstacionamento, empresaId);
        URI uri = uriBuilder.path("/estacionamento/{id}").buildAndExpand(estacionamento.getId()).toUri();
        return ResponseEntity.created(uri).body(estacionamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estacionamento> atualizar(@PathVariable("id") Long estacionamentoId,
                                                    @RequestBody @Valid EstacionamentoFormUpdateDTO dadosEstacionamento,
                                                    Authentication authentication
    ) {
        Long empresaId = userInformationService.getEmpresaId(authentication);
        Estacionamento estacionamento = estacionamentoService.atualizar(empresaId, estacionamentoId, dadosEstacionamento);
        return ResponseEntity.ok(estacionamento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable("id") Long estacionamentoId, Authentication authentication) {
        Long empresaId = userInformationService.getEmpresaId(authentication);
        estacionamentoService.deletar(empresaId, estacionamentoId);
        return ResponseEntity.ok().build();
    }
}
