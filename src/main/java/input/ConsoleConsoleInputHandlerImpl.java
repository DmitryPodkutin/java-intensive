package input;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.ConsoleCommand;
import model.CurrencyName;
import model.DateInterval;
import model.RateCommand;

import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

@Slf4j
@RequiredArgsConstructor
public class ConsoleConsoleInputHandlerImpl implements ConsoleInputHandler {

    private final CommandValidator commandValidator;

    @Override
    public Optional<ConsoleCommand> readCommand() {
        final Scanner scanner = new Scanner(System.in);
        log.info("Ожидание ввода команды...");
        System.out.println("Варианты команд - rate TRY tomorrow, rate USD week");
        System.out.print("Введите команду: ");
        final String inputString = scanner.nextLine();
        final String[] commandsParameters = inputString.split("  ");
        if (!commandValidator.validateCommand(commandsParameters)) {
            log.warn("Введенная команда имеет неверный формат: " + inputString);
            System.out.println("Команда имеет не верный формат");
            return Optional.empty();
        }
        log.info("Прочитана команда: " + inputString);
        return Optional.of(
                new ConsoleCommand (RateCommand.valueOf(Objects.requireNonNull(commandsParameters[0].toUpperCase())),
                        CurrencyName.valueOf(Objects.requireNonNull(commandsParameters[1].toUpperCase())),
                        DateInterval.valueOf(Objects.requireNonNull(commandsParameters[2].toUpperCase())))
        );
    }
}
