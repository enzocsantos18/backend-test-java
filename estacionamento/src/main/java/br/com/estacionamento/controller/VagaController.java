package br.com.estacionamento.controller;

import br.com.estacionamento.domain.Vaga;
import br.com.estacionamento.domain.dto.in.VagaFormDTO;
import br.com.estacionamento.service.VagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/vaga")
public class VagaController {

    @Autowired
    private VagaService vagaService;

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
}
