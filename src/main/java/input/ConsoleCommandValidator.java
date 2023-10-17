package input;

import model.CurrencyName;
import model.DateInterval;

import java.util.Arrays;

public class ConsoleCommandValidator implements CommandValidator {
    @Override
    public boolean validateCommand(String[] commandsParameters) {
        if (commandsParameters.length != 3) {
            return false;
        }
        final boolean currencyNameIsValid = Arrays.stream(CurrencyName.values())
                .anyMatch(x -> x.getCurrencyName().equalsIgnoreCase(commandsParameters[1]));
        final boolean intervalIsValid = Arrays.stream(DateInterval.values())
                .anyMatch(x -> x.getCurrencyName().equalsIgnoreCase(commandsParameters[2]));
        return currencyNameIsValid && intervalIsValid;
    }
}
