package br.com.brbank.service;

import br.com.brbank.dto.ActivateDto;
import br.com.brbank.dto.TransferDto;
import br.com.brbank.entities.Account;
import br.com.brbank.entities.Transaction;
import br.com.brbank.exceptions.AccountNotFound;
import br.com.brbank.exceptions.BadRequest;
import br.com.brbank.exceptions.NotFound;
import br.com.brbank.repository.AccountRepository;
import br.com.brbank.repository.TransactionsRepository;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {

  private final AccountRepository accountRepository;

  private final TransactionsRepository transactionsRepository;

  @Autowired
  public AccountService(AccountRepository accountRepository, TransactionsRepository transactionsRepository) {
    this.accountRepository = accountRepository;
    this.transactionsRepository = transactionsRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws AccountNotFound {
    return this.accountRepository.findByEmail(username).orElseThrow(AccountNotFound::new);
  }

  public Account createAccount(Account account) {
    var isCreated = this.accountRepository.findByEmail(account.getEmail());
    if (isCreated.isPresent()) {
      throw new BadRequest("Já existe uma conta com esse email.");
    }
    String hashPassword = new BCryptPasswordEncoder().encode(account.getPassword());
    account.setPassword(hashPassword);
    return this.accountRepository.save(account);
  }

  public void deactivateAccountAndActivate(Account account) {
    account.setActive(!account.isActive());
    this.accountRepository.save(account);
  }

  public void checkAccountThenActivate(ActivateDto activateDto) {
    var acc = this.accountRepository.findByEmailAndCpf(activateDto.email(), activateDto.cpf());
    if (acc.isEmpty()) {
      throw new NotFound("Conta não encontrada para reativar");
    } else if (acc.get().isActive()) {
      throw new BadRequest("A Conta já está ativada");
    } else {
      this.deactivateAccountAndActivate(acc.get());
    }
  }

  public Long transferMoney(Account account, TransferDto transferDto) {
    Optional<Account> accountOptional = this.accountRepository.findByCpf(transferDto.cpf());
    if (accountOptional.isEmpty()) {
      throw new BadRequest("Conta não encontrada para fazer a transferencia");
    }
    Account accountToTransfer = accountOptional.get();
    var balance = account.transferMoney(accountToTransfer, transferDto.money());
    this.accountRepository.saveAll(List.of(account, accountToTransfer));
    var transaction = new Transaction(account,accountToTransfer, ZonedDateTime.now(), transferDto.money());
    this.transactionsRepository.save(transaction);
    return balance;
  }

  public Long withdrawMoney(Account account, Long money) {
    var balance = account.withdrawMoney(money, true);
    this.accountRepository.save(account);
    return balance;
  }

  public Long depositMoney(Account account, Long money) {
    account.setBalance(account.getBalance() + money);
    this.accountRepository.save(account);
    return account.getBalance();
  }

  public Account findByCpf(String cpf) {
     var accountOptional = this.accountRepository.findByCpf(cpf);
    if (accountOptional.isEmpty()) {
      throw new NotFound("Cpf não existe");
    }
    return accountOptional.get();
  }
}


