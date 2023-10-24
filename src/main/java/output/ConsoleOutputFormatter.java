package output;

import model.CurrencyRate;

import java.util.List;

import static constants.Constant.dateFormat;
import static constants.Constant.decimalFormat;

public class ConsoleOutputFormatter implements OutputFormatter {

    @Override
    public void displayExchangeRate(List<CurrencyRate> currencyRates) {
        currencyRates.forEach(currencyRate ->
                System.out.printf("%s - %s;%n", dateFormat.format(currencyRate.getDate()),
                        decimalFormat.format(currencyRate.getCurs())));
    }
}