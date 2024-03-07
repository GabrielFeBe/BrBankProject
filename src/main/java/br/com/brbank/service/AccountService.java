package br.com.brbank.service;

import br.com.brbank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService{

  @Autowired
  private final AccountRepository accountRepository;

  public  AccountService(AccountRepository accountRepository){
    this.accountRepository = accountRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws RuntimeException {
    return this.accountRepository.findByEmail(username).orElseThrow(RuntimeException::new);
  }
}
