package forecasting;

import model.CurrencyName;
import model.CurrencyRate;
import model.TelegramCommand;

import java.util.List;
import java.util.Map;

public interface ExchangeTelegramForecasterCommandRouter {
    Map<CurrencyName, List<CurrencyRate>> route(TelegramCommand telegramCommand);

}
