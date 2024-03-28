package br.com.brbank.controller;

import br.com.brbank.dto.account.AccountDto;
import br.com.brbank.dto.account.ActivateDto;
import br.com.brbank.dto.DepositDto;
import br.com.brbank.dto.OnlyCpf;
import br.com.brbank.dto.TransferDto;
import br.com.brbank.dto.WithdrawDto;
import br.com.brbank.entities.Account;
import br.com.brbank.exceptions.BadRequest;
import br.com.brbank.service.AccountService;
import br.com.brbank.service.TransactionService;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "account")
@CrossOrigin(origins = "*")
public class AccountController {

  private final AccountService accountService;

  private final TransactionService transactionService;

  @Autowired
  public AccountController(AccountService accountService, TransactionService transactionService) {
    this.accountService = accountService;
    this.transactionService = transactionService;
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
  public ResponseEntity<AccountDto> findAccount(@AuthenticationPrincipal Account account) {

    var count = this.transactionService.countTransactions(account.getId());
    if (!Objects.equals(account.getNumberOfTransfers(), count)) {
      account.setNumberOfTransfers(count);
      var acc = this.accountService.updateAccount(account);
      return ResponseEntity.status(HttpStatus.OK).body(this.transformAccountToDto(acc));
    } else {
      return ResponseEntity.status(200).body(
          this.transformAccountToDto(account)
      );
    }


  }

  private AccountDto transformAccountToDto(Account account) {
    return new AccountDto(account.getEmail(), account.getBalance(), account.getNumberOfWithdraws(),
        account.getNumberOfTransfers(),
        account.getType().getType(), account.getName());
  }

  @PutMapping(value = "deactivate")
  public ResponseEntity<String> deactivateAccount(
      @AuthenticationPrincipal Account account) {
    if (!account.isActive()) {
      throw new BadRequest("A conta já está desativada");
    }
    this.accountService.deactivateAccountAndActivate(account);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PutMapping(value = "activate")
  public ResponseEntity<String> activateAccount(@RequestBody ActivateDto activateDto) {
    this.accountService.checkAccountThenActivate(activateDto);
    return ResponseEntity.status(HttpStatus.OK).body("Conta reativada com sucesso");
  }

  @PostMapping("prepare")
  public ResponseEntity<AccountDto> prepareTransfer(@RequestBody OnlyCpf cpf) {
    var acc = transformAccountToDto(this.accountService.findByCpf(cpf.cpf()));
    return ResponseEntity.status(HttpStatus.OK).body(acc);
  }

  @PostMapping(value = "transfer")
  public ResponseEntity<Double> transferMoney(@AuthenticationPrincipal Account account, @RequestBody
  TransferDto transferDto) {
    var count = this.transactionService.countTransactions(account.getId());
    if (!Objects.equals(count, account.getNumberOfTransfers())) {
      account.setNumberOfTransfers(count);
    }
    var balance = this.accountService.transferMoney(account, transferDto);
    return ResponseEntity.status(HttpStatus.OK).body(balance);


  }

  @PostMapping(value = "withdraw")
  public ResponseEntity<Double> withdrawMoney(@AuthenticationPrincipal Account account, @RequestBody
  WithdrawDto withdrawDto) {
    Double balance = this.accountService.withdrawMoney(account, withdrawDto.money());
    return ResponseEntity.status(HttpStatus.OK).body(balance);
  }

  @PostMapping(value = "deposit")
  public ResponseEntity<Double> depositMoney(@AuthenticationPrincipal Account account, @RequestBody
  DepositDto dto) {
    Double balance = this.accountService.depositMoney(account, dto.money());
    return ResponseEntity.status(HttpStatus.OK).body(balance);
  }

}
