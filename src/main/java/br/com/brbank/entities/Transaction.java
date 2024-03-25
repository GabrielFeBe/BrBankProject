package br.com.brbank.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "transaction")
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JsonIgnore
  private Account fromThis;

  @ManyToOne
  @JsonIgnore
  private Account toThis;

  private ZonedDateTime dateOfTransaction;

  private Long ammountTransfered;
  public Transaction() {
  }

  public Transaction(Account fromThis, Account toThis, ZonedDateTime dateOfTransaction,
      Long ammountTransfered) {
    this.fromThis = fromThis;
    this.toThis = toThis;
    this.dateOfTransaction = dateOfTransaction;
    this.ammountTransfered = ammountTransfered;
  }
}
