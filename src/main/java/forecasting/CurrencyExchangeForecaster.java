package forecasting;

import model.CurrencyRate;

import java.util.List;

public interface CurrencyExchangeForecaster {
    List<CurrencyRate> getExchangeRateForTomorrow(List<CurrencyRate> currencyRates);

    List<CurrencyRate> getExchangeRatesForNextWeek(List<CurrencyRate> currencyRates);
}
