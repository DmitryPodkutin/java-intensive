package forecasting.forecastingAlgorithm;

import lombok.extern.slf4j.Slf4j;
import model.CurrencyRate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static constants.Constant.CDX;

@Slf4j
public class Average extends AbstractExchangeRateAlgorithm implements CurrencyRateCalculationAlgorithm {

    @Override
    public List<CurrencyRate> calculateExchangeRateSingle(List<CurrencyRate> exchangeRates) {
        if (exchangeRates == null || exchangeRates.isEmpty()) {
            log.info("Не предоставлены данные о курсах обмена. Возвращается пустой список.");
            return List.of();
        }
        BigDecimal averageCurs = calculateAverageCurs(exchangeRates);
        final CurrencyRate sampleRate = exchangeRates.get(0);
        Date nextDate = getNextDate(sampleRate.getDate(), 1);
        log.info("Вычисление курса обмена с следующей датой: {} и средним курсом: {}", nextDate, averageCurs);
        List<CurrencyRate> result = List.of(new CurrencyRate(sampleRate.getNominal(), nextDate, averageCurs, sampleRate.getCdx()));
        log.info("Вычисление курса обмена завершено. Результат: {}", result);
        return result;
    }

    @Override
    public List<CurrencyRate> calculateExchangeRateWeek(List<CurrencyRate> exchangeRates) {
        try {
            final int daysInWeek = 7;
            List<CurrencyRate> result = exchangeRates.stream()
                    .limit(7)
                    .sorted(Comparator.comparing(CurrencyRate::getDate))
                    .collect(Collectors.toList());
            final Date endDate = getNextDate(new Date(), daysInWeek);
            final Date startDate = result.get(result.size() - 1).getDate();
            final int daysBetween = getDaysBetween(startDate, endDate);
            for (int i = 0; i < daysBetween; i++) {
                final CurrencyRate currencyRate = result.get(result.size() - 1);
                result.add(findCurrencyRateForDate(result, currencyRate.getDate()));
            }
            log.debug("Прогноз на следующую неделю успешно сформирован");
            return result.stream().skip(result.size() - daysInWeek).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Произошла ошибка при формировании прогноза на следующую неделю", e);
        }
        return List.of();
    }

    @Override
    public List<CurrencyRate> calculateExchangeRateMonth(List<CurrencyRate> exchangeRates) {
        try {
            final int daysInMonth = 30;
            List<CurrencyRate> result = exchangeRates.stream()
                    .limit(daysInMonth)
                    .sorted(Comparator.comparing(CurrencyRate::getDate))
                    .collect(Collectors.toList());
            final Date endDate = getNextDate(new Date(), daysInMonth);
            final Date startDate = result.get(result.size() - 1).getDate();
            final int daysBetween = getDaysBetween(startDate, endDate);
            for (int i = 0; i < daysBetween; i++) {
                final CurrencyRate currencyRate = result.get(result.size() - 1);
                result.add(findCurrencyRateForDate(result, currencyRate.getDate()));
            }
            log.debug("Прогноз на следующий месяц успешно сформирован");
            return result.stream().skip(result.size() - 30).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Произошла ошибка при формировании прогноза на следующий месяц", e);
        }
        return List.of();
    }

    @Override
    CurrencyRate findCurrencyRateForDate(List<CurrencyRate> exchangeRates, Date date) {
        return new CurrencyRate(0,
                getNextDate(date, 1),
                calculateAverageCurs(exchangeRates),
                CDX);
    }

    private BigDecimal calculateAverageCurs(List<CurrencyRate> exchangeRates) {
        return BigDecimal.valueOf(exchangeRates.stream()
                .sorted(Comparator.comparing(CurrencyRate::getDate).reversed())
                .limit(7)
                .mapToDouble(currencyRate -> currencyRate.getCurs().doubleValue())
                .average()
                .orElse(0.0));
    }

    private int getDaysBetween(Date startDate, Date endDate) {
        LocalDate localStartDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localEndDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localStartDate.until(localEndDate).getDays();
    }
}
