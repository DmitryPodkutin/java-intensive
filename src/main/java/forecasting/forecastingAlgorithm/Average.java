package forecasting.forecastingAlgorithm;

import model.CurrencyRate;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class Average implements CurrencyRateCalculationAlgorithm {
    public static final double ZERO_RESULT = 0.0;

    @Override
    public BigDecimal calculateAverageExchangeRateForLastSevenDays(List<CurrencyRate> exchangeRates) {
        if (exchangeRates == null) {
            return BigDecimal.ZERO;
        }
        double averageExchangeRate = exchangeRates.stream()
                .sorted(Comparator.comparing(CurrencyRate::getDate).reversed())
                .limit(7)
                .mapToDouble(currencyRate -> currencyRate.getCurs().doubleValue())
                .average()
                .orElse(ZERO_RESULT);
        return BigDecimal.valueOf(averageExchangeRate);
    }
}
