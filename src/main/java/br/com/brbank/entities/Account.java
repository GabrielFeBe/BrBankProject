package br.com.brbank.entities;

import br.com.brbank.enums.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.UniqueConstraint;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.sql.In;
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

  private Long balance;

  private AccountType type;

  //  All of the code bellow must reset monthly,
//  I also think it's good if we create an DB to save all the Moves made on the month that passed, using the account id to link it
  private Integer numberOfWithdraws;

  private Integer numberOfTransfers;

  private Integer numberOfBankStatements;

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
    return true;
  }

  public void payingTax() {
    if (type.getType().equals("CORRENTE")) {
      if (balance < 20) {
        throw new RuntimeException();
      } else {
        this.balance -= 20L;
      }
    }
  }

}
