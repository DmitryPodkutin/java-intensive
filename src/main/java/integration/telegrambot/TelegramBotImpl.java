package integration.telegrambot;

import config.BotConfig;
import forecasting.ExchangeTelegramForecasterCommandRouter;
import input.TelegramInputHandler;
import lombok.extern.slf4j.Slf4j;
import model.CurrencyName;
import model.CurrencyRate;
import model.OutputType;
import model.TelegramCommand;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import output.TelegramOutputFormatter;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class TelegramBotImpl extends DefaultAbsSender implements LongPollingBot, TelegramBot {

    private final BotConfig botConfig;
    private final TelegramInputHandler telegramInputHandler;
    private final TelegramOutputFormatter telegramOutputFormatter;
    private final ExchangeTelegramForecasterCommandRouter forecasterCommandRouter;

    public TelegramBotImpl(DefaultBotOptions options, BotConfig botConfig, TelegramInputHandler telegramInputHandler, TelegramOutputFormatter telegramOutputFormatter, ExchangeTelegramForecasterCommandRouter forecasterCommandRouter) {
        super(options, botConfig.getToken());
        this.botConfig = botConfig;
        this.telegramInputHandler = telegramInputHandler;
        this.telegramOutputFormatter = telegramOutputFormatter;
        this.forecasterCommandRouter = forecasterCommandRouter;
    }

    @Override
    public void sendMessage(Long chatId, String message) {
        log.info("Отправка сообщения в чат (chatId: {}): {}", chatId, message);
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Ошибка при отправке сообщения: {}", e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override
    public void sendImage(String chatId, String imagePath) {
        log.info("Отправка изображения в чат (chatId: {}): {}", chatId, imagePath);
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile(new File(imagePath)));
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            log.error("Ошибка при отправке изображения: {}", e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            final Optional<TelegramCommand> command = telegramInputHandler.readCommand(chatId, messageText);
            command.ifPresent(com -> processTelegramCommand(chatId, com));
        }
    }

    @Override
    public void clearWebhook() {
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    private void processTelegramCommand(Long chatId, TelegramCommand command) {
        log.info("Обработка команды от пользователя в чате (chatId: {}): {}", chatId, command);
        final Map<CurrencyName, List<CurrencyRate>> currencyRates = forecasterCommandRouter.route(command);
        if (command.getOutputType() == OutputType.GRAPH) {
            log.info("Запрошен вывод графика курсов валют.");
            sendExchangeRateGraph(chatId, currencyRates);
        } else {
            log.info("Запрошен вывод текстового сообщения о курсах валют.");
            sendExchangeRateMessage(chatId, command, currencyRates);
        }
    }

    private void sendExchangeRateMessage(Long chatId, TelegramCommand command, Map<CurrencyName, List<CurrencyRate>> currencyRates) {
        currencyRates.forEach((currencyName, rates) -> {
            String message = formatMessage(command, currencyName, rates);
            if (!message.isEmpty()) {
                sendMessage(chatId, message);
            }
        });
    }

    private void sendExchangeRateGraph(Long chatId, Map<CurrencyName, List<CurrencyRate>> currencyRates) {
        final String imagePath = telegramOutputFormatter.formatExchangeRateAsGraph(currencyRates);
        sendImage(String.valueOf(chatId), imagePath);
    }

    private String formatMessage(TelegramCommand command, CurrencyName currencyName, List<CurrencyRate> rates) {
        if (command.getOutputType() == null) {
            return telegramOutputFormatter.formatSingleExchangeRateMessage(currencyName, rates.get(0));
        } else if (command.getOutputType() == OutputType.LIST) {
            return telegramOutputFormatter.formatExchangeRateListMessage(currencyName, rates);
        }
        return "";
    }
}
