package br.com.brbank.repository;

import br.com.brbank.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsRepository extends JpaRepository<Transaction, Integer> {

}
