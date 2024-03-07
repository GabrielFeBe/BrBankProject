package br.com.brbank.enums;

import lombok.Getter;

@Getter
public enum AccountType {
  CONTA_CORRENTE("CORRENTE"),
  POUPANCA("POUPANCA");

  private final String type;
  AccountType(String type) {
    this.type = type;
  }



}
