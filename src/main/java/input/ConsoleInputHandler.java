package input;

import model.ConsoleCommand;

import java.util.Optional;

public interface ConsoleInputHandler {
     Optional<ConsoleCommand> readCommand();
}
