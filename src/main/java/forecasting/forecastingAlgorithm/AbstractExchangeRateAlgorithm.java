package forecasting.forecastingAlgorithm;

import model.CurrencyRate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class AbstractExchangeRateAlgorithm {

    protected Date getNextDate(Date currentDate, int daysToAdd) {
        LocalDate localDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate nextLocalDate = localDate.plusDays(daysToAdd);
        return Date.from(nextLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    protected Date calculateDateToCheckFromPreviousYear(Date currentDate, int daysToSubtract) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, -daysToSubtract);
        calendar.add(Calendar.YEAR, -1);
        return calendar.getTime();
    }

    protected List<CurrencyRate> calculateExchangeRate(List<CurrencyRate> exchangeRates, int numDays) {
        if (exchangeRates == null || exchangeRates.isEmpty()) {
            return List.of();
        }
        final Date currentDate = new Date();
        List<CurrencyRate> result = new ArrayList<>();
        for (int i = 0; i < numDays; i++) {
            Date dateToCheck = calculateDateToCheckFromPreviousYear(currentDate, i);
            CurrencyRate currencyRate = findCurrencyRateForDate(exchangeRates, dateToCheck);
            if (currencyRate != null) {
                result.add(new CurrencyRate(currencyRate.getNominal(), getNextDate(currentDate, i + 1),
                        currencyRate.getCurs(), currencyRate.getCdx()));
            }
        }
        return result;
    }

    abstract CurrencyRate findCurrencyRateForDate(List<CurrencyRate> exchangeRates, Date date);
}
