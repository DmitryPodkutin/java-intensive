import data.CurrencyCsvDataParser;
import data.CurrencyDataLoader;
import data.CurrencyDataParser;
import data.CurrencyFileDataLoader;
import forecasting.CurrencyExchangeForecaster;
import forecasting.CurrencyExchangeForecasterImpl;
import forecasting.ExchangeConsoleForecasterCommandRouter;
import forecasting.ExchangeConsoleForecasterCommandRouterImpl;
import input.CommandValidator;
import input.ConsoleCommandValidator;
import input.ConsoleConsoleInputHandlerImpl;
import input.ConsoleInputHandler;
import output.ConsoleConsoleOutputFormatterImpl;
import output.ConsoleOutputFormatter;

public class CurrencyExchangeApplication {
    public static void main(String[] args) {
        final ExchangeForecasterApp app = getExchangeForecasterApp();
        app.run();
    }

    private static ExchangeForecasterApp getExchangeForecasterApp() {
        return new ExchangeForecasterApp(getInputHandler(), getCommandRouter(), getOutputFormatter());
    }

    private static ConsoleOutputFormatter getOutputFormatter() {
        return new ConsoleConsoleOutputFormatterImpl();
    }

    private static ExchangeConsoleForecasterCommandRouter getCommandRouter() {
        return new ExchangeConsoleForecasterCommandRouterImpl(getExchangeForecaster(), getDataLoader());
    }

    private static CurrencyDataLoader getDataLoader() {
        return new CurrencyFileDataLoader(getCurrencyDataParser());
    }

    private static CurrencyDataParser getCurrencyDataParser() {
        return new CurrencyCsvDataParser();
    }

    private static CurrencyExchangeForecaster getExchangeForecaster() {
        return new CurrencyExchangeForecasterImpl();
    }

    private static ConsoleInputHandler getInputHandler() {
        return new ConsoleConsoleInputHandlerImpl(getCommandValidator());
    }

    private static CommandValidator getCommandValidator() {
        return new ConsoleCommandValidator();
    }
}

