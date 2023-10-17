package data;

import model.CurrencyName;
import model.CurrencyRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static model.Constant.CURRENCY_DATA_EUR_FILE_PATH;
import static model.Constant.CURRENCY_DATA_TRY_FILE_PATH;
import static model.Constant.CURRENCY_DATA_USD_FILE_PATH;

public class CurrencyFileDataLoader implements CurrencyDataLoader {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyFileDataLoader.class);
    private final CurrencyDataParser currencyDataParser = new CurrencyCsvDataParser();

    @Override
    public List<CurrencyRate> loadCurrencyRates(CurrencyName currencyName) {
        try {
            String filePath;
            switch (currencyName) {
                case EUR:
                    filePath = CURRENCY_DATA_EUR_FILE_PATH;
                    break;
                case USD:
                    filePath = CURRENCY_DATA_USD_FILE_PATH;
                    break;
                case TRY:
                    filePath = CURRENCY_DATA_TRY_FILE_PATH;
                    break;
                default:
                    return List.of();
            }
            logger.debug("Загрузка данных о курсах валют из файла: {}", filePath);
            List<CurrencyRate> currencyRates = currencyDataParser.parseData(new FileReader(filePath));
            logger.debug("Успешно загружены данные о курсах валют");
            return currencyRates;
        } catch (FileNotFoundException e) {
            logger.error("Не удалось найти файл с данными о курсах валют", e);
            e.printStackTrace();
        }
        return List.of();
    }
}
