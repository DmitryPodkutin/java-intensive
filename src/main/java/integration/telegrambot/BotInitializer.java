package integration.telegrambot;

import config.BotConfig;
import data.CurrencyCsvDataParser;
import data.CurrencyFileDataLoader;
import forecasting.CurrencyExchangeForecaster;
import forecasting.CurrencyExchangeForecasterImpl;
import forecasting.ExchangeTelegramForecasterCommandRouter;
import forecasting.ExchangeTelegramForecasterCommandRouterImpl;
import input.TelegramCommandValidator;
import input.TelegramInputHandlerImpl;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import output.TelegramOutputFormatter;
import output.TelegramOutputFormatterImpl;

public class BotInitializer {
    public static void initializeBot() {
        try {
            final TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new TelegramBotImpl(new DefaultBotOptions(), new BotConfig(),
                    new TelegramInputHandlerImpl(new TelegramCommandValidator()), getTelegramOutputFormatter(),
                    getExchangeTelegramForecasterCommandRouter()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private static TelegramOutputFormatter getTelegramOutputFormatter() {
        return new TelegramOutputFormatterImpl();
    }

    private static ExchangeTelegramForecasterCommandRouter getExchangeTelegramForecasterCommandRouter() {
        return new ExchangeTelegramForecasterCommandRouterImpl(getCurrencyExchangeForecaster(),
                getCurrencyFileDataLoader());
    }

    private static CurrencyExchangeForecaster getCurrencyExchangeForecaster() {
        return new CurrencyExchangeForecasterImpl();
    }

    private static CurrencyFileDataLoader getCurrencyFileDataLoader() {
        return new CurrencyFileDataLoader(getCurrencyCsvDataParser());
    }

    private static CurrencyCsvDataParser getCurrencyCsvDataParser() {
        return new CurrencyCsvDataParser();
    }

}
