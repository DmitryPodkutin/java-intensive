package input;

import lombok.extern.slf4j.Slf4j;
import model.CommandOption;
import model.CurrencyName;
import model.DateInterval;
import model.ForecastAlgorithm;
import model.OutputType;
import model.RateCommand;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.stream.Stream;

import static constants.Constant.inputDateFormatter;

@Slf4j
public class TelegramCommandValidator implements CommandValidator {
    private static final int MIN_PARAM_COUNT = 6;
    private static final int MAX_PARAM_COUNT = 8;

    @Override
    public boolean validateCommand(String[] commandsParameters) {
        final int paramCount = commandsParameters.length;
        if (paramCount < MIN_PARAM_COUNT || paramCount > MAX_PARAM_COUNT) {
            log.warn("Количество параметров команды ({}) вне допустимого диапазона ({}-{}). Валидация не пройдена.",
                    paramCount, MIN_PARAM_COUNT, MAX_PARAM_COUNT);
            return false;
        }
        final boolean rateCommandValid = isRateCommandValid(commandsParameters[0]);
        final boolean currencyNameIsValid = isCurrenciesNameValid(commandsParameters[1]);
        final boolean periodOptionIsValid = isPeriodOptionValid(commandsParameters[2]);
        final boolean dateOrIntervalIsValid = isDateOrIntervalValid(commandsParameters[3]);
        final boolean algorithmOptionIsValid = isAlgorithmOptionValid(commandsParameters[4]);
        final boolean algorithmTypeIsValid = isAlgorithmTypeValid(commandsParameters[5]);
        if (paramCount == MAX_PARAM_COUNT) {
            final boolean outputOptionIsValid = isOutputOptionValid(commandsParameters[6]);
            final boolean isOutputTypeValid = isOutputTypeValid(commandsParameters[7]);
            return rateCommandValid && currencyNameIsValid && periodOptionIsValid
                    && dateOrIntervalIsValid && algorithmOptionIsValid && algorithmTypeIsValid
                    && outputOptionIsValid && isOutputTypeValid;
        }
        return rateCommandValid && currencyNameIsValid && periodOptionIsValid
                && dateOrIntervalIsValid && algorithmOptionIsValid && algorithmTypeIsValid;
    }

    private boolean isRateCommandValid(String rateParam) {
        return Arrays.stream(RateCommand.values())
                .anyMatch(rateCommand -> rateCommand.name().equalsIgnoreCase(rateParam));
    }

    private boolean isCurrenciesNameValid(String currencyParam) {
        String[] currencies = currencyParam.split(",");
        if (currencies.length > 5) {
            return false;
        }
        return Arrays.stream(currencies)
                .anyMatch(currency -> Arrays.stream(CurrencyName.values())
                        .anyMatch(currencyName -> currencyName.name().equalsIgnoreCase(currency.trim())));
    }

    private boolean isPeriodOptionValid(String periodOptionParam) {
        return Stream.of(CommandOption.DATE.getOption(), CommandOption.PERIOD.getOption())
                .anyMatch(option -> option.equalsIgnoreCase(periodOptionParam));
    }

    private boolean isDateOrIntervalValid(String intervaTypeParam) {
        if (Arrays.stream(DateInterval.values())
                .anyMatch(interval -> interval.name().equalsIgnoreCase(intervaTypeParam))) {
            return true;
        }
        try {
            LocalDate.parse(intervaTypeParam, inputDateFormatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean isAlgorithmOptionValid(String algorithmOptionParam) {
        return CommandOption.ALGORITHM.getOption().equalsIgnoreCase(algorithmOptionParam);
    }

    private boolean isAlgorithmTypeValid(String algorithmTypeParam) {
        return Arrays.stream(ForecastAlgorithm.values())
                .anyMatch(algorithm -> algorithm.name().equalsIgnoreCase(algorithmTypeParam));
    }

    private boolean isOutputOptionValid(String outputOptionParam) {
        return CommandOption.OUTPUT.getOption().equalsIgnoreCase(outputOptionParam);
    }

    private boolean isOutputTypeValid(String outputTypeParam) {
        return Arrays.stream(OutputType.values())
                .anyMatch(interval -> interval.name().equalsIgnoreCase(outputTypeParam));
    }
}
