package model;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class Constant {
    public static final char SEPARATOR = ',';
    public static final String CURRENCY_DATA_EUR_FILE_PATH = "src/main/resources/input/currency_data_eur.csv";
    public static final String CURRENCY_DATA_USD_FILE_PATH = "src/main/resources/input/currency_data_usd.csv";
    public static final String CURRENCY_DATA_TRY_FILE_PATH = "src/main/resources/input/currency_data_try.csv";
    public static final DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("E dd.MM.yyyy");
}
