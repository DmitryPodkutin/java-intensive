package input;

import lombok.RequiredArgsConstructor;
import model.Command;
import model.CurrencyName;
import model.DateInterval;

import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

@RequiredArgsConstructor
public class ConsoleInputHandler implements InputHandler {

    private final CommandValidator commandValidator;

    @Override
    public Optional<Command> readCommand() {
        final Scanner scanner = new Scanner(System.in);
        System.out.println("Варианты команд - rate TRY tomorrow, rate USD week");
        System.out.print("Введите команду: ");
        final String inputString = scanner.nextLine();
        final String[] commandsParameters = inputString.split(" ");
        if (!commandValidator.validateCommand(commandsParameters)) {
            System.out.println("Команда имеет не верный формат");
            return Optional.empty();
        }
        return Optional.of(new Command(CurrencyName.valueOf(Objects.requireNonNull(commandsParameters[1].toUpperCase())),
                DateInterval.valueOf(Objects.requireNonNull(commandsParameters[2].toUpperCase()))));
    }
}
