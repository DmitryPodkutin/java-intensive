package forecasting;

import data.CurrencyDataLoader;
import data.CurrencyFileDataLoader;
import model.Command;
import model.CurrencyRate;
import model.DateInterval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ExchangeForecasterConsoleCommandRouter implements ExchangeForecasterCommandRouter {

    private final CurrencyExchangeForecaster exchangeForecaster = new CurrencyExchangeForecasterImpl();
    private final CurrencyDataLoader dataLoader = new CurrencyFileDataLoader();
    private static final Logger logger = LoggerFactory.getLogger(ExchangeForecasterConsoleCommandRouter.class);

    public List<CurrencyRate> route(Command command) {
        try {
            logger.debug("Маршрутизация запроса для команды: {}", command);
            if (DateInterval.TOMORROW == command.getInterval()) {
                logger.debug("Выбран интервал: Завтра");
                return exchangeForecaster.getExchangeRateForTomorrow(dataLoader.loadCurrencyRates(command.getCurrencyName()));
            } else if (DateInterval.WEEK == command.getInterval()) {
                logger.debug("Выбран интервал: Неделя");
                return exchangeForecaster.getExchangeRatesForNextWeek(dataLoader.loadCurrencyRates(command.getCurrencyName()));
            } else {
                logger.warn("Неверный интервал: {}", command.getInterval());
                return List.of();
            }
        } catch (Exception e) {
            logger.error("Произошла ошибка при обработке запроса", e);
            e.printStackTrace();
        }
        return List.of();
    }
}
