package br.com.estacionamento.controller;


import br.com.estacionamento.domain.dto.in.RelatorioFormDTO;
import br.com.estacionamento.domain.estacionamento.EntradasSaidasHorarioRelatorio;
import br.com.estacionamento.domain.estacionamento.EntradasSaidasRelatorio;
import br.com.estacionamento.service.estacionamento.RelatorioService;
import br.com.estacionamento.service.security.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/relatorio")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @Autowired
    private UserInformationService userInformationService;

    @GetMapping
    public ResponseEntity<EntradasSaidasRelatorio> gerarRelatorioData(@RequestBody @Valid RelatorioFormDTO dadosRelatorio, Authentication authentication) {
        Long estacionamentoId = userInformationService.getEstacionamentoId(authentication);

        EntradasSaidasRelatorio relatorio = relatorioService.gerarEntradasSaidasPorPeriodo(dadosRelatorio, estacionamentoId);
        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/hora")
    public ResponseEntity<List<EntradasSaidasHorarioRelatorio>> gerarRelatorioHoraPorData(@RequestBody @Valid RelatorioFormDTO dadosRelatorio, Authentication authentication) {
        Long estacionamentoId = userInformationService.getEstacionamentoId(authentication);
        List<EntradasSaidasHorarioRelatorio> relatorios = relatorioService.gerarEntradasSaidasPorHorario(dadosRelatorio, estacionamentoId);
        return ResponseEntity.ok(relatorios);
    }
}
