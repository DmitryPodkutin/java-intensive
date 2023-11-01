package forecasting.forecastingAlgorithm;

import lombok.extern.slf4j.Slf4j;
import model.CurrencyRate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
public class PastYearExchangeRateAlgorithm extends AbstractExchangeRateAlgorithm
        implements CurrencyRateCalculationAlgorithm {

    @Override
    public List<CurrencyRate> calculateExchangeRateSingle(List<CurrencyRate> exchangeRates) {
        log.info("Вызван метод calculateExchangeRateSingle.");
        if (exchangeRates == null || exchangeRates.isEmpty()) {
            log.warn("Пустой или null список курсов обмена.");
            return List.of();
        }
        final Date currentDate = new Date();
        final Date oneYearAgoDate = calculateDateToCheckFromPreviousYear(currentDate, 0);
        CurrencyRate currencyRate = findCurrencyRateForDate(exchangeRates, oneYearAgoDate);
        if (currencyRate != null) {
            log.info("Рассчитан курс обмена для одной даты.");
            return List.of(new CurrencyRate(currencyRate.getNominal(), currentDate, currencyRate.getCurs(), currencyRate.getCdx()));
        } else {
            log.warn("Курс обмена для указанной даты не найден.");
            return List.of();
        }
    }

    @Override
    public List<CurrencyRate> calculateExchangeRateWeek(List<CurrencyRate> exchangeRates) {
        log.info("Начало выполнения метода calculateExchangeRateWeek.");
        final int daysOnWeek = 7;
        return calculateExchangeRate(exchangeRates, daysOnWeek);
    }

    @Override
    public List<CurrencyRate> calculateExchangeRateMonth(List<CurrencyRate> exchangeRates) {
        log.info("Начало выполнения метода calculateExchangeRateMonth.");
        final int daysOnMonth = 30;
        return calculateExchangeRate(exchangeRates, daysOnMonth);
    }

    protected CurrencyRate findCurrencyRateForDate(List<CurrencyRate> exchangeRates, Date date) {
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(date);
        while (true) {
            int targetDay = targetCalendar.get(Calendar.DAY_OF_MONTH);
            int targetMonth = targetCalendar.get(Calendar.MONTH);
            int targetYear = targetCalendar.get(Calendar.YEAR);
            for (CurrencyRate rate : exchangeRates) {
                Calendar rateCalendar = Calendar.getInstance();
                rateCalendar.setTime(rate.getDate());
                int rateDay = rateCalendar.get(Calendar.DAY_OF_MONTH);
                int rateMonth = rateCalendar.get(Calendar.MONTH);
                int rateYear = rateCalendar.get(Calendar.YEAR);
                if (rateDay == targetDay && rateMonth == targetMonth && rateYear == targetYear) {
                    return rate;
                }
            }
            targetCalendar.add(Calendar.DAY_OF_MONTH, -1);
        }
    }
}



