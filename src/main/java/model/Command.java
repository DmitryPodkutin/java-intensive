package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Command {
    private final CurrencyName currencyName;
    private final DateInterval interval;
}


