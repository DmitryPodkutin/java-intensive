import forecasting.ExchangeForecasterCommandRouter;
import input.InputHandler;
import model.Command;
import model.CurrencyRate;
import output.OutputFormatter;

import java.util.List;
import java.util.Optional;

public class ExchangeForecasterApp {
    private final InputHandler inputHandler;
    private final ExchangeForecasterCommandRouter forecasterCommandRouter;
    private final OutputFormatter outputFormatter;

    public ExchangeForecasterApp(InputHandler inputHandler,
                                 ExchangeForecasterCommandRouter commandRouter,
                                 OutputFormatter outputFormatter) {
        this.inputHandler = inputHandler;
        this.forecasterCommandRouter = commandRouter;
        this.outputFormatter = outputFormatter;
    }

    public void run() {
        final Optional<Command> command = inputHandler.readCommand();
        command.ifPresent(com -> {
            final List<CurrencyRate> currencyRates = forecasterCommandRouter.route(com);
            outputFormatter.displayExchangeRate(currencyRates);
        });
    }
}
