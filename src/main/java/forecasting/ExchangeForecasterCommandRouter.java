package forecasting;

import model.Command;
import model.CurrencyRate;

import java.util.List;

public interface ExchangeForecasterCommandRouter {
    List<CurrencyRate> route(Command command);
}
