package br.com.estacionamento.controller.usuario;

import br.com.estacionamento.domain.dto.in.UsuarioFormDTO;
import br.com.estacionamento.domain.dto.in.UsuarioFormUpdateDTO;
import br.com.estacionamento.domain.usuario.Usuario;
import br.com.estacionamento.service.security.UserInformationService;
import br.com.estacionamento.service.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UserInformationService userInformationService;
    
    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody @Valid UsuarioFormDTO usuarioFormDTO, Authentication authentication) {
        Long estacionamentoId = userInformationService.getEstacionamentoId(authentication);
        Usuario usuario = usuarioService.criar(usuarioFormDTO, estacionamentoId);
        return ResponseEntity.status(201).body(usuario);
    }

    @PutMapping
    public ResponseEntity<Usuario> atualizar(@RequestBody @Valid UsuarioFormUpdateDTO dadosUsuario, Authentication authentication) {
        Usuario usuarioLogado = userInformationService.getUsuario(authentication);
        Usuario usuario = usuarioService.atualizar(dadosUsuario, usuarioLogado);
        return ResponseEntity.ok(usuario);
    }


}
