package input;

import model.Command;
import model.CurrencyName;
import model.DateInterval;

import java.util.Scanner;

public class ConsoleInputHandler implements InputHandler {

    CommandValidator commandValidator = new ConsoleCommandValidator();

    @Override
    public Command readCommand() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Варианты команд - rate TRY tomorrow, rate USD week");
        System.out.print("Введите команду: ");
        final String inputString = scanner.nextLine();
        final String[] commandsParameters = inputString.split(" ");
        if (!commandValidator.validateCommand(commandsParameters)) {
            System.out.println("Команда имеет не верный формат");
            return null;
        }
        final Command command = Command.builder()
                .currencyName(CurrencyName.valueOf(commandsParameters[1].toUpperCase()))
                .interval(DateInterval.valueOf(commandsParameters[2].toUpperCase()))
                .build();

        return command;
    }
}
