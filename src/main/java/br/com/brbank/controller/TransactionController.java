package br.com.brbank.controller;

import br.com.brbank.dto.transaction.AccountDtoForTransactions;
import br.com.brbank.dto.transaction.TransactionResponseDto;
import br.com.brbank.dto.transaction.TransactionsDto;
import br.com.brbank.entities.Account;
import br.com.brbank.entities.Transaction;
import br.com.brbank.service.TransactionService;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "transaction")
public class TransactionController {

  @Autowired
  private final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @GetMapping
  public ResponseEntity<TransactionResponseDto> getTransaction(
      @AuthenticationPrincipal Account account) {
    var res = new TransactionResponseDto();
    for (Transaction transaction : this.transactionService.transactionsMonth(account.getId())
    ) {
      if (Objects.equals(transaction.getFromThis().getId(), account.getId())) {
        res.addTransactionSent(
            new TransactionsDto(transaction.getAmmountTransfered(),
                transaction.getDateOfTransaction(),
                new AccountDtoForTransactions(transaction.getFromThis().getName(),
                    transaction.getFromThis().getEmail(), transaction.getFromThis()
                    .getType())
                ,
                new AccountDtoForTransactions(transaction.getToThis().getName(),
                    transaction.getToThis().getEmail(),
                    transaction.getToThis().getType())
            )
        );
      } else {
        res.addTransactionReceived(
            new TransactionsDto(transaction.getAmmountTransfered(),
                transaction.getDateOfTransaction(),
                new AccountDtoForTransactions(transaction.getFromThis().getName(),
                    transaction.getFromThis().getEmail(), transaction.getFromThis()
                    .getType())
                ,
                new AccountDtoForTransactions(transaction.getToThis().getName(),
                    transaction.getToThis().getEmail(),
                    transaction.getToThis().getType())
            )
        );
      }
    }
    return ResponseEntity.status(HttpStatus.OK).body(res);
  }

  @GetMapping("count")
  public ResponseEntity<Long> countTransactions(@AuthenticationPrincipal Account account) {
    return ResponseEntity.status(HttpStatus.OK).body(this.transactionService.countTransactions(
        account.getId()
    ));
  }

}
