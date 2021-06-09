package br.com.estacionamento.controller;

import br.com.estacionamento.domain.Empresa;
import br.com.estacionamento.domain.dto.in.EmpresaFormDTO;
import br.com.estacionamento.domain.dto.in.RelatorioFormDTO;
import br.com.estacionamento.domain.relatorios.EntradasSaidasRelatorio;
import br.com.estacionamento.service.EmpresaService;
import br.com.estacionamento.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/relatorio")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping
    public ResponseEntity<EntradasSaidasRelatorio> gerarRelatorioData(@Valid @RequestBody RelatorioFormDTO dadosRelatorio){
        try {

            EntradasSaidasRelatorio relatorio = relatorioService.gerarEntradasSaidasPorPeriodo(dadosRelatorio);
            return ResponseEntity.ok(relatorio);
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }


}
