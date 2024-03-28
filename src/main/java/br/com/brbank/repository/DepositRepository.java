package br.com.brbank.repository;

import br.com.brbank.entities.Deposit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DepositRepository extends JpaRepository<Deposit, Integer> {

  @Query("SELECT d FROM Deposit d WHERE MONTH(t.dateOfTransaction) = ?1 AND d.account.id = ?2")
  List<Deposit> findByDateAndPerson(Integer month, Integer accId);

}
