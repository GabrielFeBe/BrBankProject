package br.com.brbank.repository;

import br.com.brbank.entities.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositRepository extends JpaRepository<Deposit, Integer> {

}
