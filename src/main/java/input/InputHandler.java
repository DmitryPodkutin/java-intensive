package input;

import model.Command;

import java.util.Optional;

public interface InputHandler {
     Optional<Command> readCommand();
}
