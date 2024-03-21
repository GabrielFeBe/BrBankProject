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
@Setter
@Getter
public class Withdraw {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JsonIgnore
  private Account account;

  private ZonedDateTime dateOfWithdraw;

  private Long amountWithdrew;

}
