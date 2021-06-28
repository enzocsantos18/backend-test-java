package br.com.estacionamento.controller.usuario;

import br.com.estacionamento.domain.dto.in.usuario.UsuarioFormDTO;
import br.com.estacionamento.domain.dto.in.usuario.UsuarioFormUpdateDTO;
import br.com.estacionamento.domain.dto.out.usuario.RespostaUsuarioDTO;
import br.com.estacionamento.domain.usuario.Usuario;
import br.com.estacionamento.service.security.UserInformationService;
import br.com.estacionamento.service.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UserInformationService userInformationService;

    @GetMapping
    public ResponseEntity<RespostaUsuarioDTO> buscar(Authentication authentication) {
        Usuario usuarioLogado = userInformationService.getUsuario(authentication);
        RespostaUsuarioDTO usuario = new RespostaUsuarioDTO(usuarioLogado);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/{estacionamentoId}")
    public ResponseEntity<List<RespostaUsuarioDTO>> listar(@PathVariable("estacionamentoId") Long estacionamentoId,Authentication authentication) {
        Usuario usuarioLogado = userInformationService.getUsuario(authentication);
        List<RespostaUsuarioDTO> usuarios = usuarioService.listar(usuarioLogado, estacionamentoId);
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<RespostaUsuarioDTO> criar(@RequestBody @Valid UsuarioFormDTO usuarioFormDTO, Authentication authentication) {
        Long estacionamentoId = userInformationService.getEstacionamentoId(authentication);
        RespostaUsuarioDTO usuario = usuarioService.criar(usuarioFormDTO, estacionamentoId);
        return ResponseEntity.status(201).body(usuario);
    }

    @PutMapping
    public ResponseEntity<RespostaUsuarioDTO> atualizar(@RequestBody @Valid UsuarioFormUpdateDTO dadosUsuario, Authentication authentication) {
        Usuario usuarioLogado = userInformationService.getUsuario(authentication);
        RespostaUsuarioDTO usuario = usuarioService.atualizar(dadosUsuario, usuarioLogado);
        return ResponseEntity.ok(usuario);
    }
}
