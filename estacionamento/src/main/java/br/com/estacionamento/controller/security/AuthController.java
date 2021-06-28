package br.com.estacionamento.controller.security;

import br.com.estacionamento.domain.dto.in.security.LoginFormDTO;
import br.com.estacionamento.domain.dto.out.security.TokenDto;
import br.com.estacionamento.service.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDto> logar(@Valid @RequestBody LoginFormDTO dadosLogin) {
        UsernamePasswordAuthenticationToken login = dadosLogin.converter();

        Authentication authentication = authenticationManager.authenticate(login);
        String token = tokenService.gerarToken(authentication);
        TokenDto tokenResposta = new TokenDto(token, "Bearer");
        return ResponseEntity.ok(tokenResposta);
    }
}
