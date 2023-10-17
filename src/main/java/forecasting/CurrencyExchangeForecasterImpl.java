package forecasting;

import data.CurrencyCsvDataParser;
import forecasting.forecastingAlgorithm.CurrencyRateArithmeticMeanAlgorithm;
import forecasting.forecastingAlgorithm.CurrencyRateCalculationAlgorithm;
import model.CurrencyRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;

public class CurrencyExchangeForecasterImpl implements CurrencyExchangeForecaster {
    private final CurrencyRateCalculationAlgorithm currencyRateCalculationAlgorithm =
            new CurrencyRateArithmeticMeanAlgorithm();
    private static final Logger logger = LoggerFactory.getLogger(CurrencyExchangeForecasterImpl.class);


    @Override
    public List<CurrencyRate> getExchangeRateForTomorrow(List<CurrencyRate> currencyRates) {
        try {
            BigDecimal curs = currencyRateCalculationAlgorithm.calculateAverageExchangeRateForLastSevenDays(currencyRates);
            logger.debug("Рассчитан средний курс за последние семь дней: {}", curs);
            return currencyRates.stream()
                    .limit(1)
                    .map(currencyRate -> CurrencyRate.builder()
                            .nominal(currencyRate.getNominal())
                            .date(getNextDate(new Date(), 1))
                            .curs(curs)
                            .cdx(currencyRate.getCdx()).build()).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Произошла ошибка при расчете курса на завтра", e);
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
                CurrencyRate currencyRate = result.get(result.size() - 1);
                result.add(new CurrencyRate(0,
                        getNextDate(currencyRate.getDate(), 1),
                        currencyRateCalculationAlgorithm.calculateAverageExchangeRateForLastSevenDays(result),
                        currencyRate.getCdx()));
            }
            logger.debug("Прогноз на следующую неделю успешно сформирован");
            return result;
        } catch (Exception e) {
            logger.error("Произошла ошибка при формировании прогноза на следующую неделю", e);
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
