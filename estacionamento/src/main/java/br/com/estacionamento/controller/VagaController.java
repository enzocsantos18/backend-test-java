package br.com.estacionamento.controller;

import br.com.estacionamento.domain.Vaga;
import br.com.estacionamento.domain.dto.in.VagaFormDTO;
import br.com.estacionamento.service.VagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/vaga")
public class VagaController {

    @Autowired
    private VagaService vagaService;

    @GetMapping("{empresa}/{estacionamento}")
    public ResponseEntity<List<Vaga>> buscar(
            @PathVariable("empresa") Long empresaId,
            @PathVariable("estacionamento") Long estacionamentoId
    ) {
        List<Vaga> vagas = vagaService.buscar(empresaId, estacionamentoId);
        return ResponseEntity.ok(vagas);
    }

    @PostMapping
    public ResponseEntity<Vaga> adicionarVaga(@RequestBody @Valid VagaFormDTO dadosVaga) {

        Vaga vaga = vagaService.adicionar(dadosVaga);

        return ResponseEntity.ok(vaga);
    }

    @DeleteMapping("{empresa}/{estacionamento}/{tipoVaga}")
    public ResponseEntity deletar(
            @PathVariable("empresa") Long empresaId,
            @PathVariable("estacionamento") Long estacionamentoId,
            @PathVariable("tipoVaga") Long tipoVagaId
    ) {

        vagaService.deletar(empresaId, estacionamentoId, tipoVagaId);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Vaga> atualizar(
            @RequestBody @Valid VagaFormDTO dadosVaga
    ) {

        Vaga vaga = vagaService.atualizar(dadosVaga);
        return ResponseEntity.ok(vaga);
    }
}
