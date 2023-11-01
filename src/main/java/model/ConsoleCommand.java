package model;

import lombok.Getter;

@Getter
public class ConsoleCommand extends Command {
    private final CurrencyName currencyName;

    public ConsoleCommand(RateCommand rateCommand, CurrencyName currencyName, DateInterval interval) {
        super(rateCommand, interval);
        this.currencyName = currencyName;
    }
}


