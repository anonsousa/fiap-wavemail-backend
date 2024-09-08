package br.com.fiap.wavemail.controllers;

import br.com.fiap.wavemail.domain.dto.auth.AuthUserLoginDto;
import br.com.fiap.wavemail.domain.dto.auth.TokenDto;
import br.com.fiap.wavemail.domain.dto.user.UserAddDto;
import br.com.fiap.wavemail.domain.dto.user.UserReturnDto;
import br.com.fiap.wavemail.domain.model.UserEntity;
import br.com.fiap.wavemail.domain.service.UserService;
import br.com.fiap.wavemail.domain.service.auth.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<UserReturnDto> register(@RequestBody @Valid UserAddDto user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Valid AuthUserLoginDto authUserLogin){
        // Cria o token de autenticação
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(authUserLogin.email(), authUserLogin.password());

        // Autentica o usuário
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o usuário autenticado (que contém o ID do banco de dados)
        UserEntity user = (UserEntity) authentication.getPrincipal();

        // Gera o token JWT
        TokenDto token = tokenService.generateToken(user);

        // Retorna o ID do banco e o token
        return ResponseEntity.status(HttpStatus.OK).body(new TokenDto(token.userId(), token.email(), token.token()));
    }











}
