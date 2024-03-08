package br.com.brbank.service;

import br.com.brbank.entities.Account;
import br.com.brbank.exceptions.AccountAlreadyExist;
import br.com.brbank.exceptions.AccountNotFound;
import br.com.brbank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {

  private final AccountRepository accountRepository;

  @Autowired
  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws AccountNotFound {
    return this.accountRepository.findByEmail(username).orElseThrow(AccountNotFound::new);
  }

  public Account createAccount(Account account) {
    var isCreated = this.accountRepository.findByEmail(account.getEmail());
    if (isCreated.isPresent()) {
      throw new AccountAlreadyExist();
    }
    String hashPassword = new BCryptPasswordEncoder().encode(account.getPassword());
    account.setPassword(hashPassword);
    return this.accountRepository.save(account);
  }
}
