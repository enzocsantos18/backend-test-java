package br.com.estacionamento.controller;

import br.com.estacionamento.domain.Movimentacao;
import br.com.estacionamento.domain.dto.in.MovimentacaoFormDTO;
import br.com.estacionamento.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/movimentacao")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService movimentacaoService;


    @PostMapping("/entrada")
    public ResponseEntity<Movimentacao> entrada(@RequestBody @Valid MovimentacaoFormDTO dadosMovimentacao, UriComponentsBuilder uriBuilder) {
        Movimentacao movimentacao = movimentacaoService.entrada(dadosMovimentacao);
        return ResponseEntity.ok(movimentacao);
    }

    @PostMapping("/saida")
    public ResponseEntity<Movimentacao> saida(@RequestBody @Valid MovimentacaoFormDTO dadosMovimentacao, UriComponentsBuilder uriBuilder) {
        Movimentacao movimentacao = movimentacaoService.saida(dadosMovimentacao);
        return ResponseEntity.ok(movimentacao);
    }
}
