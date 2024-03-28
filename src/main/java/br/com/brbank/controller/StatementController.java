package br.com.brbank.controller;

import br.com.brbank.service.DepositService;
import br.com.brbank.service.TransactionService;
import br.com.brbank.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("statement")
@CrossOrigin("*")
public class StatementController {

  private final WithdrawService withdrawService;
  private final DepositService depositService;
  private final TransactionService transactionService;

  @Autowired
  public StatementController(WithdrawService withdrawService, DepositService depositService,
      TransactionService transactionService) {
    this.withdrawService = withdrawService;
    this.depositService = depositService;
    this.transactionService = transactionService;
  }
}
