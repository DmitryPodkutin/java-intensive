package data;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;
import model.CurrencyRate;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static constants.Constant.SEPARATOR;

@Slf4j
public class CurrencyCsvDataParser implements CurrencyDataParser {

    @Override
    public List<CurrencyRate> parseData(InputStreamReader inputStreamReader) {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(CurrencyRate.class)
                .withColumnSeparator(SEPARATOR).withSkipFirstDataRow(true);
        List<CurrencyRate> elements = new ArrayList<>();
        try {
            MappingIterator<CurrencyRate> iterator = mapper
                    .readerFor(CurrencyRate.class)
                    .with(schema)
                    .readValues(inputStreamReader);
            elements.addAll(iterator.readAll());
        } catch (IOException e) {
            log.error("Failed to read CSV file", e);
        }
        return elements;
    }
}
