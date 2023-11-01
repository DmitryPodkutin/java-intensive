package forecasting.forecastingAlgorithm;

import forecasting.forecastingAlgorithm.component.LinearRegression;
import lombok.extern.slf4j.Slf4j;
import model.CurrencyRate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static constants.Constant.CDX;

@Slf4j
public class LinearRegressionExchangeRateAlgorithm extends AbstractExchangeRateAlgorithm
        implements CurrencyRateCalculationAlgorithm {

    @Override
    public List<CurrencyRate> calculateExchangeRateSingle(List<CurrencyRate> exchangeRates) {
        log.info("Начало выполнения метода calculateExchangeRateSingle.");
        if (exchangeRates == null || exchangeRates.isEmpty()) {
            log.warn("Курсы обмена пусты или равны null.");
            return List.of();
        }
        final Date currentDate = new Date();
        final Date oneYearAgoDate = calculateDateToCheckFromPreviousYear(currentDate, 0);
        CurrencyRate currencyRate = findCurrencyRateForDate(exchangeRates, oneYearAgoDate);
        if (currencyRate != null) {
            log.info("Рассчитан курс обмена для одной даты.");
            return List.of(new CurrencyRate(currencyRate.getNominal(), currentDate, currencyRate.getCurs(),
                    currencyRate.getCdx()));
        } else {
            log.warn("Для указанной даты не найден курс обмена.");
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
        List<CurrencyRate> filteredRates = new ArrayList<>();
        for (CurrencyRate rate : exchangeRates) {
            Calendar rateCalendar = Calendar.getInstance();
            rateCalendar.setTime(rate.getDate());
            int rateYear = rateCalendar.get(Calendar.YEAR);
            int rateMonth = rateCalendar.get(Calendar.MONTH);
            Calendar lastMonthCalendar = Calendar.getInstance();
            lastMonthCalendar.setTime(date);
            lastMonthCalendar.add(Calendar.MONTH, -1);
            int lastMonthYear = lastMonthCalendar.get(Calendar.YEAR);
            int lastMonth = lastMonthCalendar.get(Calendar.MONTH);
            if (rateYear == lastMonthYear && rateMonth == lastMonth) {
                filteredRates.add(rate);
            }
        }
        if (filteredRates.isEmpty()) {
            return null;
        }
        double[] x = new double[filteredRates.size()];
        double[] y = new double[filteredRates.size()];
        for (int i = 0; i < filteredRates.size(); i++) {
            CurrencyRate rate = filteredRates.get(i);
            x[i] = rate.getDate().getTime();
            y[i] = rate.getCurs().doubleValue();
        }
        LinearRegression regression = new LinearRegression(x, y);
        long targetTime = date.getTime();
        double predictedValue = regression.predict(targetTime);
        return new CurrencyRate(1, new Date(targetTime), BigDecimal.valueOf(predictedValue), CDX);
    }

}
