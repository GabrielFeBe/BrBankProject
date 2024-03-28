package br.com.brbank.repository;

import br.com.brbank.entities.Withdraw;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WithdrawRepository extends JpaRepository<Withdraw, Integer> {

  @Query("SELECT w FROM Withdraw w WHERE MONTH(t.dateOfTransaction) = ?1 AND w.account.id = ?2")
  List<Withdraw> findByDateAndPerson(Integer month, Integer accId);

  @Query("SELECT COUNT(w) FROM Withdraw w WHERE MONTH(t.dateOfTransaction) = ?1 AND w.account.id = ?2")
  Integer countWithdraws(Integer month, Integer accId);

}
