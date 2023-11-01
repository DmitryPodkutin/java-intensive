package forecasting;

import data.CurrencyDataLoader;
import forecasting.forecastingAlgorithm.Average;
import forecasting.forecastingAlgorithm.CurrencyRateCalculationAlgorithm;
import forecasting.forecastingAlgorithm.LinearRegressionExchangeRateAlgorithm;
import forecasting.forecastingAlgorithm.MysticalExchangeRateAlgorithm;
import forecasting.forecastingAlgorithm.PastYearExchangeRateAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.CurrencyName;
import model.CurrencyRate;
import model.ForecastAlgorithm;
import model.TelegramCommand;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

@Slf4j
@RequiredArgsConstructor
public class ExchangeTelegramForecasterCommandRouterImpl implements ExchangeTelegramForecasterCommandRouter {

    private final CurrencyExchangeForecaster exchangeForecaster;
    private final CurrencyDataLoader dataLoader;

    public Map<CurrencyName, List<CurrencyRate>> route(TelegramCommand telegramCommand) {
        try {
            log.debug("Маршрутизация запроса для команды: {}", telegramCommand);
            final CurrencyRateCalculationAlgorithm algorithm = chooseAlgorithm(telegramCommand);
            Map<CurrencyName, List<CurrencyRate>> ratesByCurrency = new HashMap<>();

            switch (telegramCommand.getInterval()) {
                case TOMORROW:
                    log.debug("Выбран интервал: Завтра");
                    ratesByCurrency = processRatesForInterval(
                            telegramCommand, algorithm,
                            (currencyName, algo) -> exchangeForecaster.getExchangeRateForTomorrow(
                                    dataLoader.loadCurrencyRates(currencyName), algo)
                    );
                    break;
                case WEEK:
                    log.debug("Выбран интервал: Неделя");
                    ratesByCurrency = processRatesForInterval(
                            telegramCommand, algorithm,
                            (currencyName, algo) -> exchangeForecaster.getExchangeRatesForWeek(
                                    dataLoader.loadCurrencyRates(currencyName), algo)
                    );
                    break;
                case MONTH:
                    log.debug("Выбран интервал: Месяц");
                    ratesByCurrency = processRatesForInterval(
                            telegramCommand, algorithm,
                            (currencyName, algo) -> exchangeForecaster.getExchangeRatesForMonth(
                                    dataLoader.loadCurrencyRates(currencyName), algo)
                    );
                    break;
                default:
                    log.warn("Неверный интервал: {}", telegramCommand.getInterval());
                    break;
            }

            return ratesByCurrency;
        } catch (Exception e) {
            log.error("Произошла ошибка при обработке запроса", e);
            return Collections.emptyMap();
        }
    }

    private Map<CurrencyName, List<CurrencyRate>> processRatesForInterval(
            TelegramCommand telegramCommand,
            CurrencyRateCalculationAlgorithm algorithm,
            BiFunction<CurrencyName, CurrencyRateCalculationAlgorithm, List<CurrencyRate>> rateFetcher
    ) {
        Map<CurrencyName, List<CurrencyRate>> ratesByCurrency = new HashMap<>();

        for (CurrencyName currencyName : telegramCommand.getCurrencyName()) {
            List<CurrencyRate> rates = rateFetcher.apply(currencyName, algorithm);
            ratesByCurrency.put(currencyName, rates);
        }

        return ratesByCurrency;
    }

    private CurrencyRateCalculationAlgorithm chooseAlgorithm(TelegramCommand telegramCommand) {
        ForecastAlgorithm algorithmParam = telegramCommand.getForecastAlgorithm();
        if (algorithmParam == ForecastAlgorithm.LAST_YEAR) {
            return new PastYearExchangeRateAlgorithm();
        } else if (algorithmParam == ForecastAlgorithm.MYSTICAL) {
            return new MysticalExchangeRateAlgorithm();
        } else if (algorithmParam == ForecastAlgorithm.LINEAR_REGRESSION) {
            return new LinearRegressionExchangeRateAlgorithm();
        }
        return new Average();
    }
}

