package data;

import model.CurrencyRate;

 import java.io.InputStreamReader;
import java.util.List;

public interface CurrencyDataParser {
    List<CurrencyRate> parseData(InputStreamReader fileReader);
}
