package br.com.brbank.controller;

import br.com.brbank.dto.auth.AuthDto;
import br.com.brbank.dto.auth.TokenDto;
import br.com.brbank.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "auth")
@CrossOrigin(origins = "*")
public class AuthController {

  private final AuthenticationManager authenticationManager;

  private final TokenService tokenService;

  @Autowired
  public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
  }


  @PostMapping(value = "/login")
  public ResponseEntity<TokenDto> login(@RequestBody AuthDto authDto) {
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        authDto.email(), authDto.password());
    var auth = this.authenticationManager.authenticate(authenticationToken);
    return ResponseEntity.status(200)
        .body(new TokenDto(this.tokenService.generateToken(auth.getName())));
  }

}
