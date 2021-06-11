package br.com.estacionamento.controller;

import br.com.estacionamento.domain.Estacionamento;
import br.com.estacionamento.domain.dto.in.EstacionamentoFormDTO;
import br.com.estacionamento.domain.dto.in.EstacionamentoFormUpdateDTO;
import br.com.estacionamento.service.EstacionamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/estacionamento")
public class EstacionamentoController {

    @Autowired
    private EstacionamentoService estacionamentoService;

    @PostMapping
    public ResponseEntity<Estacionamento> criar(@RequestBody @Valid EstacionamentoFormDTO dadosEstacionamento, UriComponentsBuilder uriBuilder) {
        Estacionamento estacionamento = estacionamentoService.criar(dadosEstacionamento);
        URI uri = uriBuilder.path("/estacionamento/{estacionamento}/{id}").buildAndExpand(dadosEstacionamento.getId_empresa(), estacionamento.getId()).toUri();
        return ResponseEntity.created(uri).body(estacionamento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Estacionamento>> listagemPorEmpresa(@PathVariable("id") Long empresaId) {
        List<Estacionamento> listagem = estacionamentoService.listagem(empresaId);
        return ResponseEntity.ok(listagem);
    }

    @GetMapping("/{empresa}/{id}")
    public ResponseEntity<Estacionamento> buscarPorId(@PathVariable("empresa") Long empresaId, @PathVariable("id") Long estacionamentoId) {
        Estacionamento estacionamento = estacionamentoService.buscar(empresaId, estacionamentoId);
        return ResponseEntity.ok(estacionamento);
    }

    @DeleteMapping("/{empresa}/{id}")
    public ResponseEntity deletar(@PathVariable("empresa") Long empresaId,
                                  @PathVariable("id") Long estacionamentoId) {
        estacionamentoService.deletar(empresaId, estacionamentoId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{empresa}/{id}")
    public ResponseEntity<Estacionamento> atualizar(@PathVariable("empresa") Long empresaId,
                                                    @PathVariable("id") Long estacionamentoId,
                                                    @RequestBody @Valid EstacionamentoFormUpdateDTO dadosEstacionamento
    ) {
        Estacionamento estacionamento = estacionamentoService.atualizar(empresaId, estacionamentoId, dadosEstacionamento);
        return ResponseEntity.ok(estacionamento);
    }
}
