package data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.CurrencyName;
import model.CurrencyRate;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static constants.Constant.CURRENCY_DATA_AMD_FILE_PATH;
import static constants.Constant.CURRENCY_DATA_BGN_FILE_PATH;
import static constants.Constant.CURRENCY_DATA_EUR_FILE_PATH;
import static constants.Constant.CURRENCY_DATA_TRY_FILE_PATH;
import static constants.Constant.CURRENCY_DATA_USD_FILE_PATH;

@Slf4j
@RequiredArgsConstructor
public class CurrencyFileDataLoader implements CurrencyDataLoader {

    private final CurrencyDataParser currencyDataParser;

    @Override
    public List<CurrencyRate> loadCurrencyRates(CurrencyName currencyName) {
        String filePath = setFilePath(currencyName);
        if (filePath.isEmpty()) {
            return List.of();
        }
        log.debug("Загрузка данных о курсах валют из файла: {}", filePath);
        try (FileReader reader = new FileReader(filePath)) {
            List<CurrencyRate> currencyRates = currencyDataParser.parseData(reader);
            log.debug("Успешно загружены данные о курсах валют");
            return currencyRates;
        } catch (FileNotFoundException e) {
            log.error("Не удалось найти файл с данными о курсах валют", e);
        } catch (IOException e) {
            log.error("Произошла ошибка при чтении файла", e);
        }
        return List.of();
    }

    private String setFilePath(CurrencyName currencyName) {
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
            case BGN:
                filePath = CURRENCY_DATA_BGN_FILE_PATH;
                break;
            case AMD:
                filePath = CURRENCY_DATA_AMD_FILE_PATH;
                break;
            default:
                filePath = "";
        }
        return filePath;
    }
}
