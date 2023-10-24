import data.CurrencyCsvDataParser;
import data.CurrencyFileDataLoader;
import forecasting.CurrencyExchangeForecasterImpl;
import forecasting.ExchangeForecasterCommandRouter;
import forecasting.ExchangeForecasterConsoleCommandRouter;
import input.ConsoleCommandValidator;
import input.ConsoleInputHandler;
import input.InputHandler;
import model.Command;
import model.CurrencyRate;
import output.ConsoleOutputFormatter;
import output.OutputFormatter;

import java.util.List;
import java.util.Optional;

public class ExchangeForecasterApp {
    private final InputHandler inputHandler;
    private final ExchangeForecasterCommandRouter forecasterCommandRouter;
    private final OutputFormatter outputFormatter;

    public ExchangeForecasterApp() {
        this.inputHandler = new ConsoleInputHandler(new ConsoleCommandValidator());
        this.forecasterCommandRouter = new ExchangeForecasterConsoleCommandRouter(
                new CurrencyExchangeForecasterImpl(),
                new CurrencyFileDataLoader(new CurrencyCsvDataParser())
        );
        this.outputFormatter = new ConsoleOutputFormatter();
    }

    public void run() {
        final Optional<Command> command = inputHandler.readCommand();
        command.ifPresent(com -> {
            final List<CurrencyRate> currencyRates = forecasterCommandRouter.route(com);
            outputFormatter.displayExchangeRate(currencyRates);
        });
    }
}
