package br.com.brbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
public class BrbankApplication {

  public static void main(String[] args) {
    SpringApplication.run(BrbankApplication.class, args);
  }

}
