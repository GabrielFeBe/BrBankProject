package br.com.brbank.dto.statement;

import java.time.ZonedDateTime;

public record ValuesDateDto(ZonedDateTime date, Double value) {

}
