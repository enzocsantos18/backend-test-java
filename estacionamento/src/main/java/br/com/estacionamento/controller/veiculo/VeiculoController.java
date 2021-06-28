package br.com.estacionamento.controller.veiculo;

import br.com.estacionamento.domain.veiculo.Veiculo;
import br.com.estacionamento.domain.dto.in.VeiculoFormDTO;
import br.com.estacionamento.service.veiculo.VeiculoService;
import br.com.estacionamento.service.security.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private UserInformationService userInformationService;

    @GetMapping("/{placa}")
    public ResponseEntity<Veiculo> buscar(@PathVariable("placa") String placa, Authentication authentication) {
        Long estacionamentoId = userInformationService.getEstacionamentoId(authentication);
        Veiculo veiculo = veiculoService.buscar(placa, estacionamentoId);
        return ResponseEntity.ok(veiculo);
    }

    @PostMapping
    public ResponseEntity<Veiculo> criar(
            @RequestBody @Valid VeiculoFormDTO dadosVeiculo,
            Authentication authentication
    ) {
        Long estacionamentoId = userInformationService.getEstacionamentoId(authentication);
        Veiculo veiculo = veiculoService.criar(dadosVeiculo, estacionamentoId);
        return ResponseEntity.status(201).body(veiculo);
    }

    @DeleteMapping("/{placa}")
    public ResponseEntity deletar(@PathVariable("placa") String placa, Authentication authentication) {
        Long estacionamentoId = userInformationService.getEstacionamentoId(authentication);
        veiculoService.deletar(placa, estacionamentoId);
        return ResponseEntity.noContent().build();
    }

}
