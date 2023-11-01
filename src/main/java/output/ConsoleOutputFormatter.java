package output;

import model.CurrencyRate;

import java.util.List;

public interface ConsoleOutputFormatter {
     void displayExchangeRate(List<CurrencyRate> currencyRates);
}
