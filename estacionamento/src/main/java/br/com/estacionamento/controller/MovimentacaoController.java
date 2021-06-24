package br.com.estacionamento.controller;

import br.com.estacionamento.domain.estacionamento.Movimentacao;
import br.com.estacionamento.domain.dto.in.MovimentacaoFormDTO;
import br.com.estacionamento.service.estacionamento.MovimentacaoService;
import br.com.estacionamento.service.security.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/movimentacao")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService movimentacaoService;

    @Autowired
    private UserInformationService userInformationService;

    @PostMapping("/entrada")
    public ResponseEntity<Movimentacao> entrada(@RequestBody @Valid MovimentacaoFormDTO dadosMovimentacao, Authentication authentication) {
        Long estacionamentoId = userInformationService.getEstacionamentoId(authentication);

        Movimentacao movimentacao = movimentacaoService.entrada(dadosMovimentacao, estacionamentoId);
        return ResponseEntity.ok(movimentacao);
    }

    @PostMapping("/saida")
    public ResponseEntity<Movimentacao> saida(@RequestBody @Valid MovimentacaoFormDTO dadosMovimentacao, Authentication authentication) {
        Long estacionamentoId = userInformationService.getEstacionamentoId(authentication);
        Movimentacao movimentacao = movimentacaoService.saida(dadosMovimentacao, estacionamentoId);
        return ResponseEntity.ok(movimentacao);
    }
}
