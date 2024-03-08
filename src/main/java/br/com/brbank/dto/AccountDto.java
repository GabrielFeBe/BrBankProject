package br.com.brbank.dto;


public record AccountDto(String email, Long balance, Integer numberOfWithdraws,
                         Integer numberOfTransfers, Integer numberOfBankStatements, String type) {


}
