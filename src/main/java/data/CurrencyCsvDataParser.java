package data;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import model.CurrencyRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static model.Constant.SEPARATOR;

public class CurrencyCsvDataParser implements CurrencyDataParser {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyCsvDataParser.class);

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
            logger.error("Failed to read CSV file", e);
        }
        return elements;
    }
}
