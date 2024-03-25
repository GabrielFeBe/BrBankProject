package br.com.brbank.repository;

import br.com.brbank.entities.Transaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionsRepository extends JpaRepository<Transaction, Integer> {

  @Query("SELECT t FROM Transaction t WHERE MONTH(t.dateOfTransaction) = ?1 AND (t.toThis.id = ?2 OR t.fromThis.id = ?2)")
  List<Transaction> findByDateAndPerson(Integer month, Integer accId);

  @Query("SELECT COUNT(t) FROM Transaction t WHERE MONTH(t.dateOfTransaction) = ?1 AND (t.toThis.id = ?2 OR t.fromThis.id = ?2)")
  Long countTransactions(Integer month, Integer accId);
}
