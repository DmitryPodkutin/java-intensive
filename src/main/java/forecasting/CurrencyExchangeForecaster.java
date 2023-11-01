package forecasting;

import forecasting.forecastingAlgorithm.CurrencyRateCalculationAlgorithm;
import model.CurrencyRate;

import java.util.List;

public interface CurrencyExchangeForecaster {
    List<CurrencyRate> getExchangeRateForTomorrow(List<CurrencyRate> currencyRates, CurrencyRateCalculationAlgorithm algorithm);

    List<CurrencyRate> getExchangeRatesForWeek(List<CurrencyRate> currencyRates, CurrencyRateCalculationAlgorithm algorithm);

    List<CurrencyRate> getExchangeRatesForMonth(List<CurrencyRate> currencyRates, CurrencyRateCalculationAlgorithm algorithm);
}
