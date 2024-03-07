package br.com.brbank.repository;

import br.com.brbank.entities.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Integer, Account> {
Optional<Account> findByEmail(String email);
}
