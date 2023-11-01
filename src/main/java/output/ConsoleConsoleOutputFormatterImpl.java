package output;

import model.CurrencyRate;

import java.util.List;

import static constants.Constant.outputDateFormat;
import static constants.Constant.decimalFormat;

public class ConsoleConsoleOutputFormatterImpl implements ConsoleOutputFormatter {

    @Override
    public void displayExchangeRate(List<CurrencyRate> currencyRates) {
        currencyRates.forEach(currencyRate ->
                System.out.printf("%s - %s;%n", outputDateFormat.format(currencyRate.getDate()),
                        decimalFormat.format(currencyRate.getCurs())));
    }
}