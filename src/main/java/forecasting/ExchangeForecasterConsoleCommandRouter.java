package forecasting;

import data.CurrencyDataLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Command;
import model.CurrencyRate;
import model.DateInterval;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ExchangeForecasterConsoleCommandRouter implements ExchangeForecasterCommandRouter {

    private final CurrencyExchangeForecaster exchangeForecaster;
    private final CurrencyDataLoader dataLoader;

    public List<CurrencyRate> route(Command command) {
        try {
            log.debug("Маршрутизация запроса для команды: {}", command);
            if (DateInterval.TOMORROW == command.getInterval()) {
                log.debug("Выбран интервал: Завтра");
                return exchangeForecaster.getExchangeRateForTomorrow(dataLoader.loadCurrencyRates(command.getCurrencyName()));
            } else if (DateInterval.WEEK == command.getInterval()) {
                log.debug("Выбран интервал: Неделя");
                return exchangeForecaster.getExchangeRatesForNextWeek(dataLoader.loadCurrencyRates(command.getCurrencyName()));
            } else {
                log.warn("Неверный интервал: {}", command.getInterval());
                return List.of();
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при обработке запроса", e);
        }
        return List.of();
    }
}
