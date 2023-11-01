package constants;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class Constant {
    public static final String CDX = "CDX";
    public static final char SEPARATOR = ',';
    public static final String CURRENCY_DATA_EUR_FILE_PATH = "src/main/resources/input/currency_data_eur.csv";
    public static final String CURRENCY_DATA_USD_FILE_PATH = "src/main/resources/input/currency_data_usd.csv";
    public static final String CURRENCY_DATA_TRY_FILE_PATH = "src/main/resources/input/currency_data_try.csv";
    public static final String CURRENCY_DATA_BGN_FILE_PATH = "src/main/resources/input/currency_data_bng.csv";
    public static final String CURRENCY_DATA_AMD_FILE_PATH = "src/main/resources/input/currency_data_amd.csv";
    public static final String RATE_GRAPH_PNG = "src/main/resources/output/exchange_rate_graph.png";
    public static final DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
    public static final SimpleDateFormat outputDateFormat = new SimpleDateFormat("E dd.MM.yyyy");
    public static final DateTimeFormatter inputDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
}
