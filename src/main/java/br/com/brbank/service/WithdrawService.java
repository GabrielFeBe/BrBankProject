package br.com.brbank.service;

import br.com.brbank.dto.statement.ValuesDateDto;
import br.com.brbank.repository.WithdrawRepository;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithdrawService {

  private final WithdrawRepository withdrawRepository;

  @Autowired
  public WithdrawService(WithdrawRepository withdrawRepository) {
    this.withdrawRepository = withdrawRepository;
  }

  public Integer countWithdraws(Integer id) {
    return this.withdrawRepository.countWithdraws(ZonedDateTime.now().getMonthValue(), id);
  }

  public List<ValuesDateDto> monthWithdraws(Integer id) {
    return this.withdrawRepository.findByDateAndPerson(ZonedDateTime.now().getMonthValue(), id)
        .stream().map(withdraw ->
            new ValuesDateDto(withdraw.getDateOfWithdraw(), withdraw.getAmountWithdrew() * -1))
        .toList();
  }

}
