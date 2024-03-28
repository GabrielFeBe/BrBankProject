package br.com.brbank.service;

import br.com.brbank.dto.statement.ValuesDateDto;
import br.com.brbank.entities.Account;
import br.com.brbank.repository.DepositRepository;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepositService {

  private final DepositRepository depositRepository;

  @Autowired
  public DepositService(DepositRepository depositRepository) {
    this.depositRepository = depositRepository;
  }

  public List<ValuesDateDto> monthDeposits(Integer id) {
    return this.depositRepository.findByDateAndPerson(ZonedDateTime.now().getMonthValue(), id)
        .stream().map(deposit ->
            new ValuesDateDto(deposit.getDateOfDeposit(), deposit.getAmountDeposited())
        ).toList();
  }

}
