package br.com.brbank.controller;

import br.com.brbank.dto.AccountDto;
import br.com.brbank.entities.Account;
import br.com.brbank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "account")
public class AccountController {

  private AccountService accountService;

  @Autowired
  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @PostMapping
  public ResponseEntity<AccountDto> createAccount(@RequestBody Account account) {
    if (account.getId() != null) {
      throw new RuntimeException();
    } else {
      return ResponseEntity.status(200).body(this.transformAccountToDto(
          this.accountService.createAccount(account)));
    }
  }

  @GetMapping
  ResponseEntity<AccountDto> findAccount(@AuthenticationPrincipal Account account) {
    return ResponseEntity.status(200).body(
        this.transformAccountToDto(account)
    );
  }

  private AccountDto transformAccountToDto(Account account) {
    return new AccountDto(account.getEmail(), account.getBalance(), account.getNumberOfWithdraws(),
        account.getNumberOfTransfers(), account.getNumberOfBankStatements(),
        account.getType().getType());
  }


}
