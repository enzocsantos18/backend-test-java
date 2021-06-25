package br.com.estacionamento.controller;

import br.com.estacionamento.domain.usuario.TipoUsuario;
import br.com.estacionamento.service.usuario.TipoUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/tipo/usuario")
public class TipoUsuarioController {

    @Autowired
    private TipoUsuarioService tipoUsuarioService;

    @GetMapping
    public ResponseEntity<List<TipoUsuario>> listar() {
        List<TipoUsuario> tipos = tipoUsuarioService.listar();
        return ResponseEntity.ok(tipos);
    }


}
