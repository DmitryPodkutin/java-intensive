import forecasting.ExchangeForecasterCommandRouter;
import forecasting.ExchangeForecasterConsoleCommandRouter;
import input.ConsoleInputHandler;
import input.InputHandler;
import model.CurrencyRate;
import output.ConsoleOutputFormatter;

import java.util.List;

public class CurrencyExchangeApplication {
    public static void main(String[] args) {
        InputHandler inputHandler = new ConsoleInputHandler();
        final var command = inputHandler.readCommand();
        ExchangeForecasterCommandRouter forecasterCommandRouter = new ExchangeForecasterConsoleCommandRouter();
        if (command != null) {
            final List<CurrencyRate> currencyRates = forecasterCommandRouter.route(command);
            ConsoleOutputFormatter outputFormatter = new ConsoleOutputFormatter();
            outputFormatter.displayExchangeRate(currencyRates);
        }
    }
}
