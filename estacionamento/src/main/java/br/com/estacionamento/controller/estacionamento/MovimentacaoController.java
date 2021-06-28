package br.com.estacionamento.controller.estacionamento;

import br.com.estacionamento.domain.dto.in.estacionamento.MovimentacaoFormDTO;
import br.com.estacionamento.domain.dto.out.estacionamento.RespostaMovimentacaoDTO;
import br.com.estacionamento.domain.estacionamento.Movimentacao;
import br.com.estacionamento.service.estacionamento.MovimentacaoService;
import br.com.estacionamento.service.security.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/movimentacao")
public class MovimentacaoController {
    @Autowired
    private MovimentacaoService movimentacaoService;

    @Autowired
    private UserInformationService userInformationService;

    @PostMapping("/entrada")
    public ResponseEntity<RespostaMovimentacaoDTO> entrada(@RequestBody @Valid MovimentacaoFormDTO dadosMovimentacao, Authentication authentication) {
        Long estacionamentoId = userInformationService.getEstacionamentoId(authentication);
        RespostaMovimentacaoDTO movimentacao = movimentacaoService.entrada(dadosMovimentacao, estacionamentoId);
        return ResponseEntity.ok(movimentacao);
    }

    @PostMapping("/saida")
    public ResponseEntity<RespostaMovimentacaoDTO> saida(@RequestBody @Valid MovimentacaoFormDTO dadosMovimentacao, Authentication authentication) {
        Long estacionamentoId = userInformationService.getEstacionamentoId(authentication);
        RespostaMovimentacaoDTO movimentacao = movimentacaoService.saida(dadosMovimentacao, estacionamentoId);
        return ResponseEntity.ok(movimentacao);
    }
}
