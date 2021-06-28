package br.com.estacionamento.controller.veiculo;

import br.com.estacionamento.domain.veiculo.TipoVeiculo;
import br.com.estacionamento.service.security.UserInformationService;
import br.com.estacionamento.service.veiculo.TipoVeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tipo/veiculo")
public class TipoVeiculoController {

    @Autowired
    private UserInformationService userInformationService;
    @Autowired
    private TipoVeiculoService tipoVeiculoService;

    @GetMapping
    public ResponseEntity<List<TipoVeiculo>> listar() {
        List<TipoVeiculo> listar = tipoVeiculoService.listar();
        return ResponseEntity.ok(listar);
    }
}
