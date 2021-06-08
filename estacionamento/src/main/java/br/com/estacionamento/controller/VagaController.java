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
    ){
        try{
            List<Vaga> vagas = vagaService.buscar(empresaId, estacionamentoId);
            return ResponseEntity.ok(vagas);
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Vaga> adicionarVaga(@Valid @RequestBody VagaFormDTO dadosVaga){
        try {

            Vaga vaga = vagaService.adicionar(dadosVaga);

            return ResponseEntity.ok(vaga);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("{empresa}/{estacionamento}/{tipoVaga}")
    public ResponseEntity deletar(
            @PathVariable("empresa") Long empresaId,
            @PathVariable("estacionamento") Long estacionamentoId,
            @PathVariable("tipoVaga") Long tipoVagaId
    ){
        try {
            vagaService.deletar(empresaId, estacionamentoId, tipoVagaId);
            return ResponseEntity.ok().build();

        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<Vaga> atualizar(
            @RequestBody @Valid VagaFormDTO dadosVaga
    ){
        try{
            Vaga vaga = vagaService.atualizar(dadosVaga);
            return ResponseEntity.ok(vaga);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
