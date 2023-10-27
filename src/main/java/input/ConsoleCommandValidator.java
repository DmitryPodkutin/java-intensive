package input;

import model.CurrencyName;
import model.DateInterval;
import model.RateCommand;

import java.util.Arrays;

public class ConsoleCommandValidator implements CommandValidator {
    @Override
    public boolean validateCommand(String[] commandsParameters) {
        if (commandsParameters.length != 3) {
            return false;
        }
        final boolean firstCommand = Arrays.stream(RateCommand.values())
                .anyMatch(x -> x.name().equalsIgnoreCase(commandsParameters[0]));
        final boolean currencyNameIsValid = Arrays.stream(CurrencyName.values())
                .anyMatch(x -> x.name().equalsIgnoreCase(commandsParameters[1]));
        final boolean intervalIsValid = Arrays.stream(DateInterval.values())
                .anyMatch(x -> x.name().equalsIgnoreCase(commandsParameters[2]));
        return currencyNameIsValid && intervalIsValid && firstCommand;
    }
}
