package br.com.estacionamento.controller.estacionamento;

import br.com.estacionamento.domain.dto.out.estacionamento.RespostaEstacionamentoDTO;
import br.com.estacionamento.domain.estacionamento.Estacionamento;
import br.com.estacionamento.domain.dto.in.estacionamento.EstacionamentoFormDTO;
import br.com.estacionamento.domain.dto.in.estacionamento.EstacionamentoFormUpdateDTO;
import br.com.estacionamento.service.estacionamento.EstacionamentoService;
import br.com.estacionamento.service.security.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/estacionamento")
public class EstacionamentoController {
    @Autowired
    private EstacionamentoService estacionamentoService;

    @Autowired
    private UserInformationService userInformationService;

    @GetMapping
    public ResponseEntity<List<RespostaEstacionamentoDTO>> listar(Authentication authentication) {
        Long empresaId = userInformationService.getEmpresaId(authentication);
        List<RespostaEstacionamentoDTO> listagem = estacionamentoService.listar(empresaId);
        return ResponseEntity.ok(listagem);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespostaEstacionamentoDTO> buscar(@PathVariable("id") Long estacionamentoId, Authentication authentication) {
        Long empresaId = userInformationService.getEmpresaId(authentication);
        RespostaEstacionamentoDTO estacionamento = estacionamentoService.buscar(empresaId, estacionamentoId);
        return ResponseEntity.ok(estacionamento);
    }

    @PostMapping
    public ResponseEntity<RespostaEstacionamentoDTO> criar(@RequestBody @Valid EstacionamentoFormDTO dadosEstacionamento,
                                                Authentication authentication) {
        Long empresaId = userInformationService.getEmpresaId(authentication);
        RespostaEstacionamentoDTO estacionamento = estacionamentoService.criar(dadosEstacionamento, empresaId);
        return ResponseEntity.status(201).body(estacionamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespostaEstacionamentoDTO> atualizar(@PathVariable("id") Long estacionamentoId,
                                                    @RequestBody @Valid EstacionamentoFormUpdateDTO dadosEstacionamento,
                                                    Authentication authentication
    ) {
        Long empresaId = userInformationService.getEmpresaId(authentication);
        RespostaEstacionamentoDTO estacionamento = estacionamentoService.atualizar(empresaId, estacionamentoId, dadosEstacionamento);
        return ResponseEntity.ok(estacionamento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable("id") Long estacionamentoId, Authentication authentication) {
        Long empresaId = userInformationService.getEmpresaId(authentication);
        estacionamentoService.deletar(empresaId, estacionamentoId);
        return ResponseEntity.noContent().build();
    }
}
