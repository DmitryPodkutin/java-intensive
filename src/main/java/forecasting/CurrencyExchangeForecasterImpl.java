package forecasting;

import forecasting.forecastingAlgorithm.Average;
import forecasting.forecastingAlgorithm.CurrencyRateCalculationAlgorithm;
import lombok.extern.slf4j.Slf4j;
import model.CurrencyRate;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CurrencyExchangeForecasterImpl implements CurrencyExchangeForecaster {
    private final CurrencyRateCalculationAlgorithm currencyRateCalculationAlgorithm =
            new Average();


    @Override
    public List<CurrencyRate> getExchangeRateForTomorrow(List<CurrencyRate> currencyRates) {
        try {
            BigDecimal curs = currencyRateCalculationAlgorithm.calculateAverageExchangeRateForLastSevenDays(currencyRates);
            log.debug("Рассчитан средний курс за последние семь дней: {}", curs);
            return currencyRates.stream()
                    .limit(1)
                    .map(currencyRate -> new CurrencyRate(currencyRate.getNominal(),
                            getNextDate(new Date(), 1), curs, currencyRate.getCdx()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Произошла ошибка при расчете курса на завтра", e);
        }
        return List.of();
    }

    @Override
    public List<CurrencyRate> getExchangeRatesForNextWeek(List<CurrencyRate> currencyRates) {
        try {
            List<CurrencyRate> result = currencyRates.stream()
                    .limit(7)
                    .sorted(Comparator.comparing(CurrencyRate::getDate))
                    .collect(Collectors.toList());
            final Date endDate = getNextDate(new Date(), 7);
            final Date startDate = result.get(result.size() - 1).getDate();
            final int daysBetween = getDaysBetween(startDate, endDate);
            for (int i = 0; i < daysBetween; i++) {
                final CurrencyRate currencyRate = result.get(result.size() - 1);
                result.add(new CurrencyRate(0,
                        getNextDate(currencyRate.getDate(), 1),
                        currencyRateCalculationAlgorithm.calculateAverageExchangeRateForLastSevenDays(result),
                        currencyRate.getCdx()));
            }
            log.debug("Прогноз на следующую неделю успешно сформирован");
            return result;
        } catch (Exception e) {
            log.error("Произошла ошибка при формировании прогноза на следующую неделю", e);
        }
        return List.of();
    }


    private Date getNextDate(Date currentDate, int dateOffset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, dateOffset);
        return calendar.getTime();
    }

    private static int getDaysBetween(Date startDate, Date endDate) {
        long milliseconds = endDate.getTime() - startDate.getTime();
        return (int) Duration.ofMillis(milliseconds).toDays();
    }
}
