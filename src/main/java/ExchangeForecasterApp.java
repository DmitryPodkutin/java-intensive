import forecasting.ExchangeConsoleForecasterCommandRouter;
import input.ConsoleInputHandler;
import integration.telegrambot.BotInitializer;
import lombok.RequiredArgsConstructor;
import model.ConsoleCommand;
import model.CurrencyRate;
import output.ConsoleOutputFormatter;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ExchangeForecasterApp {
    private final ConsoleInputHandler consoleInputHandler;
    private final ExchangeConsoleForecasterCommandRouter forecasterCommandRouter;
    private final ConsoleOutputFormatter consoleOutputFormatter;

    public void run() {
        final Optional<ConsoleCommand> command = consoleInputHandler.readCommand();
        command.ifPresent(com -> {
            final List<CurrencyRate> currencyRates = forecasterCommandRouter.route(com);
            consoleOutputFormatter.displayExchangeRate(currencyRates);
        });
        BotInitializer.initializeBot();
    }
}
