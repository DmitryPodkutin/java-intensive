package output;

import model.CurrencyRate;

import java.util.List;

public interface OutputFormatter {
     void displayExchangeRate(List<CurrencyRate> currencyRates);
}
