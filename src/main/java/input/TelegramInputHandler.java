package input;

import model.TelegramCommand;

import java.util.Optional;

public interface TelegramInputHandler {
    Optional<TelegramCommand> readCommand(Long chatId, String command);
}
