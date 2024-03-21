package br.com.brbank.repository;

import br.com.brbank.entities.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawRepository extends JpaRepository<Withdraw, Integer> {

}
