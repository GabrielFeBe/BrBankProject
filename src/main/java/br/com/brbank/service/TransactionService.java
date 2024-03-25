package br.com.brbank.service;

import br.com.brbank.entities.Transaction;
import br.com.brbank.repository.TransactionsRepository;
import jakarta.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  @Autowired
  private final TransactionsRepository transactionsRepository;

  public TransactionService(TransactionsRepository transactionsRepository) {
    this.transactionsRepository = transactionsRepository;
  }

  @Transactional
  public List<Transaction> transactionsMonth(Integer id) {
    return this.transactionsRepository.findByDateAndPerson(
        ZonedDateTime.now().getMonthValue(), id);
  }

  public Long countTransactions(Integer id) {
    return this.transactionsRepository.countTransactions(ZonedDateTime.now().getMonthValue(), id);
  }

}
