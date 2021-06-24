package br.com.estacionamento.controller;

import br.com.estacionamento.domain.estacionamento.Vaga;
import br.com.estacionamento.domain.dto.in.VagaFormDTO;
import br.com.estacionamento.service.estacionamento.VagaService;
import br.com.estacionamento.service.security.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/vaga")
public class VagaController {

    @Autowired
    private VagaService vagaService;

    @Autowired
    private UserInformationService userInformationService;

    @GetMapping
    public ResponseEntity<List<Vaga>> buscar(Authentication authentication) {
        Long estacionamentoId = userInformationService.getEstacionamentoId(authentication);

        List<Vaga> vagas = vagaService.buscar(estacionamentoId);
        return ResponseEntity.ok(vagas);
    }

    @PostMapping
    public ResponseEntity<Vaga> adicionarVaga(@RequestBody @Valid VagaFormDTO dadosVaga, Authentication authentication) {
        Long estacionamentoId = userInformationService.getEstacionamentoId(authentication);

        Vaga vaga = vagaService.adicionar(dadosVaga, estacionamentoId);

        return ResponseEntity.ok(vaga);
    }

    @PutMapping
    public ResponseEntity<Vaga> atualizar(
            @RequestBody @Valid VagaFormDTO dadosVaga,
            Authentication authentication
    ) {
        Long estacionamentoId = userInformationService.getEstacionamentoId(authentication);

        Vaga vaga = vagaService.atualizar(dadosVaga, estacionamentoId);
        return ResponseEntity.ok(vaga);
    }

    @DeleteMapping("/{tipoVaga}")
    public ResponseEntity deletar(
            @PathVariable("tipoVaga") Long tipoVagaId,
            Authentication authentication
    ) {
        Long estacionamentoId = userInformationService.getEstacionamentoId(authentication);
        vagaService.deletar(estacionamentoId, tipoVagaId);
        return ResponseEntity.ok().build();
    }


}
