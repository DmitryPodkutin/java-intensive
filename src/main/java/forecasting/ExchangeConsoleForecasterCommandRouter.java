package forecasting;

import model.ConsoleCommand;
import model.CurrencyRate;

import java.util.List;

public interface ExchangeConsoleForecasterCommandRouter {
    List<CurrencyRate> route(ConsoleCommand consoleCommand);
}
