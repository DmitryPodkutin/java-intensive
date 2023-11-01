package forecasting.forecastingAlgorithm;

import model.CurrencyRate;

import java.util.List;

public interface CurrencyRateCalculationAlgorithm {
    List<CurrencyRate> calculateExchangeRateSingle(List<CurrencyRate> exchangeRates);

    List<CurrencyRate> calculateExchangeRateWeek(List<CurrencyRate> exchangeRates);

    List<CurrencyRate> calculateExchangeRateMonth(List<CurrencyRate> exchangeRates);

}
