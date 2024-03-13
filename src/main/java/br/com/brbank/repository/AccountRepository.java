package br.com.brbank.repository;

import br.com.brbank.entities.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

  Optional<Account> findByEmail(String email);

  Optional<Account> findByEmailAndCpf(String email, String cpf);

  Optional<Account> findByCpf(String cpf);
}
