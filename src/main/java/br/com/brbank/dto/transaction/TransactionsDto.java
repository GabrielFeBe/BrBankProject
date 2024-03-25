package br.com.brbank.dto.transaction;

import br.com.brbank.dto.transaction.AccountDtoForTransactions;
import java.time.ZonedDateTime;

public record TransactionsDto(Long amount, ZonedDateTime dateTime, AccountDtoForTransactions from, AccountDtoForTransactions to) {

}
