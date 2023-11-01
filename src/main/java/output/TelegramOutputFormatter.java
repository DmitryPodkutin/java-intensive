package output;

import model.CurrencyName;
import model.CurrencyRate;

import java.util.List;
import java.util.Map;

public interface TelegramOutputFormatter {

    String formatSingleExchangeRateMessage(CurrencyName currencyName, CurrencyRate currencyRate);

    String formatExchangeRateListMessage(CurrencyName currencyName, List<CurrencyRate> currencyRates);

    String formatExchangeRateAsGraph(Map<CurrencyName, List<CurrencyRate>> currencyRates);
}
