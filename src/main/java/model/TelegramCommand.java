package model;

import lombok.Getter;

import java.util.List;

@Getter
public class TelegramCommand extends Command {
    private final Long chatId;
    private final List<CurrencyName> currencyName;
    private final CommandOption optionPeriod;
    private final CommandOption optionAlgorithm;
    private final ForecastAlgorithm forecastAlgorithm;
    private final CommandOption output;
    private final OutputType outputType;

    public TelegramCommand(Long chatId, RateCommand rateCommand, List<CurrencyName> currencyName, CommandOption optionPeriod,
                           DateInterval interval, CommandOption optionAlgorithm, ForecastAlgorithm forecastAlgorithm,
                           CommandOption output, OutputType outputType) {
        super(rateCommand, interval);
        this.chatId = chatId;
        this.currencyName = currencyName;
        this.optionPeriod = optionPeriod;
        this.optionAlgorithm = optionAlgorithm;
        this.forecastAlgorithm = forecastAlgorithm;
        this.output = output;
        this.outputType = outputType;
    }

    public TelegramCommand(Long chatId, RateCommand rateCommand, List<CurrencyName> currencyName, CommandOption optionPeriod,
                           DateInterval interval, CommandOption optionAlgorithm, ForecastAlgorithm forecastAlgorithm) {
        this(chatId, rateCommand, currencyName, optionPeriod, interval, optionAlgorithm, forecastAlgorithm, null, null);
    }
}


