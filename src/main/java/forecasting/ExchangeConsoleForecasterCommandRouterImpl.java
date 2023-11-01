package forecasting;

import data.CurrencyDataLoader;
import forecasting.forecastingAlgorithm.Average;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.ConsoleCommand;
import model.CurrencyRate;
import model.DateInterval;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ExchangeConsoleForecasterCommandRouterImpl implements ExchangeConsoleForecasterCommandRouter {

    private final CurrencyExchangeForecaster exchangeForecaster;
    private final CurrencyDataLoader dataLoader;

    public List<CurrencyRate> route(ConsoleCommand consoleCommand) {
        try {
            log.debug("Маршрутизация запроса для команды: {}", consoleCommand);
            if (DateInterval.TOMORROW == consoleCommand.getInterval()) {
                log.debug("Выбран интервал: Завтра");
                return exchangeForecaster.getExchangeRateForTomorrow(
                        dataLoader.loadCurrencyRates(consoleCommand.getCurrencyName()), new Average());
            } else if (DateInterval.WEEK == consoleCommand.getInterval()) {
                log.debug("Выбран интервал: Неделя");
                return exchangeForecaster.getExchangeRatesForWeek(
                        dataLoader.loadCurrencyRates(consoleCommand.getCurrencyName()), new Average());
            } else {
                log.warn("Неверный интервал: {}", consoleCommand.getInterval());
                return List.of();
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при обработке запроса", e);
        }
        return List.of();
    }
}
