package br.com.brbank.controller;

import br.com.brbank.exceptions.AccountNotFound;
import br.com.brbank.exceptions.BadRequest;
import br.com.brbank.exceptions.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor {


  @ExceptionHandler(AccountNotFound.class)
  public ResponseEntity<String> accountNotFound(AccountNotFound e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body("Conta não encontrada");
  }

  @ExceptionHandler(NotFound.class)
  public ResponseEntity<String> notFoundException(NotFound e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }


  @ExceptionHandler(BadRequest.class)
  public ResponseEntity<String> badRequestHandler(BadRequest e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(e.getMessage());
  }


}
