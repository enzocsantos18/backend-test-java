package br.com.estacionamento.controller;


import br.com.estacionamento.domain.dto.in.RelatorioFormDTO;
import br.com.estacionamento.domain.relatorios.EntradasSaidasHorarioRelatorio;
import br.com.estacionamento.domain.relatorios.EntradasSaidasRelatorio;
import br.com.estacionamento.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/relatorio")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping
    public ResponseEntity<EntradasSaidasRelatorio> gerarRelatorioData(@RequestBody @Valid RelatorioFormDTO dadosRelatorio) {
        EntradasSaidasRelatorio relatorio = relatorioService.gerarEntradasSaidasPorPeriodo(dadosRelatorio);
        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/hora")
    public ResponseEntity<List<EntradasSaidasHorarioRelatorio>> gerarRelatorioHoraPorData(@RequestBody @Valid  RelatorioFormDTO dadosRelatorio) {
        List<EntradasSaidasHorarioRelatorio> relatorios = relatorioService.gerarEntradasSaidasPorHorario(dadosRelatorio);
        return ResponseEntity.ok(relatorios);
    }
}
