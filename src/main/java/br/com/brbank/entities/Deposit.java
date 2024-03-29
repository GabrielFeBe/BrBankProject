package br.com.brbank.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Deposit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JsonIgnore
  private Account account;

  private ZonedDateTime dateOfDeposit;

  private Double amountDeposited;

  public Deposit() {
  }

  public Deposit(Account account, ZonedDateTime dateOfDeposit, Double amountDeposited) {
    this.account = account;
    this.dateOfDeposit = dateOfDeposit;
    this.amountDeposited = amountDeposited;
  }

}
