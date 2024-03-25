package br.com.brbank.dto.transaction;

import br.com.brbank.enums.AccountType;

public record AccountDtoForTransactions(String name, String email, AccountType type) {

}
