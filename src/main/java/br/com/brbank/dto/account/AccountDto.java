package br.com.brbank.dto.account;


public record AccountDto(String email, Double balance, Integer numberOfWithdraws,
                         Integer numberOfTransfers, String type,
                         String name) {


}
