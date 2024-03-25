package br.com.brbank.dto.transaction;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class TransactionResponseDto {

  public final ArrayList<TransactionsDto> received = new ArrayList<>();
  public final ArrayList<TransactionsDto> sent = new ArrayList<>();

  public void addTransactionReceived(TransactionsDto transactionsDto) {
    this.received.add(transactionsDto);
  }

  public void addTransactionSent(TransactionsDto transactionsDto) {
    this.sent.add(transactionsDto);
  }

}
