import data.CurrencyCsvDataParser;
import data.CurrencyDataLoader;
import data.CurrencyDataParser;
import data.CurrencyFileDataLoader;
import forecasting.CurrencyExchangeForecaster;
import forecasting.CurrencyExchangeForecasterImpl;
import forecasting.ExchangeForecasterCommandRouter;
import forecasting.ExchangeForecasterConsoleCommandRouter;
import forecasting.forecastingAlgorithm.Average;
import input.CommandValidator;
import input.ConsoleCommandValidator;
import input.ConsoleInputHandler;
import input.InputHandler;
import output.ConsoleOutputFormatter;
import output.OutputFormatter;

public class CurrencyExchangeApplication {
    public static void main(String[] args) {
        final ExchangeForecasterApp app = getExchangeForecasterApp();
        app.run();
    }

    private static ExchangeForecasterApp getExchangeForecasterApp() {
        return new ExchangeForecasterApp(getInputHandler(), getCommandRouter(), getOutputFormatter());
    }

    private static OutputFormatter getOutputFormatter() {
        return new ConsoleOutputFormatter();
    }

    private static ExchangeForecasterCommandRouter getCommandRouter() {
        return new ExchangeForecasterConsoleCommandRouter(getExchangeForecaster(), getDataLoader());
    }

    private static CurrencyDataLoader getDataLoader() {
        return new CurrencyFileDataLoader(getCurrencyDataParser());
    }

    private static CurrencyDataParser getCurrencyDataParser() {
        return new CurrencyCsvDataParser();
    }

    private static CurrencyExchangeForecaster getExchangeForecaster() {
        return new CurrencyExchangeForecasterImpl(new Average());
    }

    private static InputHandler getInputHandler() {
        return new ConsoleInputHandler(getCommandValidator());
    }

    private static CommandValidator getCommandValidator() {
        return new ConsoleCommandValidator();
    }
}

