package input;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.CommandOption;
import model.CurrencyName;
import model.DateInterval;
import model.ForecastAlgorithm;
import model.OutputType;
import model.RateCommand;
import model.TelegramCommand;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class TelegramInputHandlerImpl implements TelegramInputHandler {

    private final CommandValidator commandValidator;

    @Override
    public Optional<TelegramCommand> readCommand(Long chatId, String command) {
        final String[] commandsParameters = command.split(" ");
        if (!commandValidator.validateCommand(commandsParameters)) {
            log.warn("Команда '{}' не прошла валидацию. Отклонена.", command);
            return Optional.empty();
        }

        final RateCommand rateCommand = RateCommand.valueOf(commandsParameters[0].toUpperCase());
        final List<CurrencyName> currencyNamesList = parseCurrencyNames(commandsParameters[1]);
        final CommandOption period = CommandOption.fromOption(commandsParameters[2]);
        final DateInterval interval = DateInterval.valueOf(commandsParameters[3].toUpperCase());
        final CommandOption algorithm = CommandOption.fromOption(commandsParameters[4]);
        final ForecastAlgorithm forecastAlgorithm = ForecastAlgorithm.valueOf(commandsParameters[5].toUpperCase());

        if (commandsParameters.length < 8) {
            log.info("Команда '{}' успешно разобрана и прошла валидацию.", command);
            return Optional.of(new TelegramCommand(chatId, rateCommand, currencyNamesList, period, interval, algorithm, forecastAlgorithm));
        } else {
            final CommandOption output = CommandOption.fromOption(commandsParameters[6]);
            final OutputType outputType = OutputType.valueOf(commandsParameters[7].toUpperCase());
            log.info("Команда '{}' успешно разобрана и прошла валидацию.", command);
            return Optional.of(new TelegramCommand(chatId, rateCommand, currencyNamesList, period, interval, algorithm, forecastAlgorithm, output, outputType));
        }
    }

    private List<CurrencyName> parseCurrencyNames(String input) {
        final String[] currencyNamesArray = input.split(",");
        return Arrays.stream(currencyNamesArray)
                .map(currencyNameStr -> CurrencyName.valueOf(currencyNameStr.trim().toUpperCase()))
                .collect(Collectors.toList());
    }
}
