package br.com.brbank.controller;

import br.com.brbank.exceptions.AccountAlreadyExist;
import br.com.brbank.exceptions.AccountNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor {


  @ExceptionHandler(AccountNotFound.class)
  public ResponseEntity<String> accountNotFound(AccountNotFound e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body("Conta não encontrada");
  }

  @ExceptionHandler(AccountAlreadyExist.class)
  public ResponseEntity<String> accountAlreadyExist(AccountAlreadyExist e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body("Já existe uma conta cadastrada com esse email no servidor");
  }


}
