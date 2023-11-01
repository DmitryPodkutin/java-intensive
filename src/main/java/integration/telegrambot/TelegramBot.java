package integration.telegrambot;

public interface TelegramBot {
    void sendMessage(Long chatId, String message);

    void sendImage(String chatId, String imagePath);
}
