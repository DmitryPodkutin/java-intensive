package forecasting;

import forecasting.forecastingAlgorithm.CurrencyRateCalculationAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.CurrencyRate;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class CurrencyExchangeForecasterImpl implements CurrencyExchangeForecaster {

    @Override
    public List<CurrencyRate> getExchangeRateForTomorrow(List<CurrencyRate> currencyRates,
                                                         CurrencyRateCalculationAlgorithm algorithm) {
        return algorithm.calculateExchangeRateSingle(currencyRates);
    }

    @Override
    public List<CurrencyRate> getExchangeRatesForWeek(List<CurrencyRate> currencyRates,
                                                      CurrencyRateCalculationAlgorithm algorithm) {
        return algorithm.calculateExchangeRateWeek(currencyRates);
    }

    @Override
    public List<CurrencyRate> getExchangeRatesForMonth(List<CurrencyRate> currencyRates,
                                                       CurrencyRateCalculationAlgorithm algorithm) {
        return algorithm.calculateExchangeRateMonth(currencyRates);
    }
}
