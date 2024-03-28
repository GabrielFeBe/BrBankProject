package br.com.brbank.entities;

import br.com.brbank.enums.AccountType;
import br.com.brbank.exceptions.BadRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@Setter
public class Account implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String password;

  @Column(unique = true)
  private String email;

  private String role;

  private Double balance;

  private String name;

  private AccountType type;

  private String cpf;

  private boolean isActive = true;

  //  All of the code bellow must reset monthly,
  //  I also think it's good if we create an DB to save all the transactions made on the month that's passed, using the account id to link it
  private Integer numberOfWithdraws;

  private Integer numberOfTransfers;


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role));
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return this.isActive;
  }

  public void payingTax() {
    if (type.getType().equals("CORRENTE")) {
      if (balance < 20) {
        throw new RuntimeException();
      } else {
        this.balance -= 20D;
      }
    }
  }

  public void removingMoney(Double money) {
    if (type.getType().equals("POUPANCA") && this.balance < money) {
      throw new BadRequest("Seu saldo é menor que o dinheiro apresentado para saque");
    } else if (type.getType().equals("CORRENTE") && this.balance - money < -500) {
      throw new BadRequest(
          String.format("Na sua conta o maximo de saldo negativo permitido é: %d", 500));
    } else {
      setBalance(this.getBalance() - money);
    }
  }


  public Double withdrawMoney(Double money, Boolean isOnlyWithdraw) {
    if (type.getType().equals("POUPANCA") && this.numberOfWithdraws >= 2 && isOnlyWithdraw) {
      throw new BadRequest("Essa conta já teve o limite de saques do mês atingido");
    } else {
      this.removingMoney(money);
      if (isOnlyWithdraw) {
        this.numberOfWithdraws++;
      }
      return this.balance;
    }
  }

  public Double transferMoney(Account account, Double money) {
    if (type.getType().equals("POUPANCA") && this.numberOfTransfers >= 2) {
      throw new BadRequest("Numeros de transferencias exedidas!");
    } else {
      var value = this.withdrawMoney(money, false);
      account.setBalance(account.getBalance() + money);
      this.setNumberOfTransfers(numberOfTransfers + 1);
      return value;
    }

  }


}
