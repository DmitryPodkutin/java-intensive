package forecasting.forecastingAlgorithm;

import model.CurrencyRate;

import java.math.BigDecimal;
import java.util.List;

public interface CurrencyRateCalculationAlgorithm {
    BigDecimal calculateAverageExchangeRateForLastSevenDays(List<CurrencyRate> exchangeRates);
}
