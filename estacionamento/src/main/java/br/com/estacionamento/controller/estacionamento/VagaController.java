package br.com.estacionamento.controller.estacionamento;

import br.com.estacionamento.domain.dto.in.estacionamento.VagaFormDTO;
import br.com.estacionamento.domain.dto.out.estacionamento.RespostaVagaDTO;
import br.com.estacionamento.domain.estacionamento.Vaga;
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
    public ResponseEntity<List<RespostaVagaDTO>> buscar(Authentication authentication) {
        Long estacionamentoId = userInformationService.getEstacionamentoId(authentication);
        List<RespostaVagaDTO> vagas = vagaService.buscar(estacionamentoId);
        return ResponseEntity.ok(vagas);
    }

    @PostMapping
    public ResponseEntity<RespostaVagaDTO> criar(@RequestBody @Valid VagaFormDTO dadosVaga, Authentication authentication) {
        Long estacionamentoId = userInformationService.getEstacionamentoId(authentication);
        RespostaVagaDTO vaga = vagaService.criar(dadosVaga, estacionamentoId);
        return ResponseEntity.status(201).body(vaga);
    }

    @PutMapping
    public ResponseEntity<RespostaVagaDTO> atualizar(@RequestBody @Valid VagaFormDTO dadosVaga, Authentication authentication) {
        Long estacionamentoId = userInformationService.getEstacionamentoId(authentication);
        RespostaVagaDTO vaga = vagaService.atualizar(dadosVaga, estacionamentoId);
        return ResponseEntity.ok(vaga);
    }

    @DeleteMapping("/{tipoVaga}")
    public ResponseEntity deletar(@PathVariable("tipoVaga") Long tipoVagaId, Authentication authentication) {
        Long estacionamentoId = userInformationService.getEstacionamentoId(authentication);
        vagaService.deletar(estacionamentoId, tipoVagaId);
        return ResponseEntity.noContent().build();
    }
}
