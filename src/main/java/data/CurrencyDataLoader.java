package data;

import model.CurrencyName;
import model.CurrencyRate;

import java.util.List;

public interface CurrencyDataLoader {

    List<CurrencyRate> loadCurrencyRates(CurrencyName currencyName);

}
