package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommandOption {
    DATE("-date"),
    PERIOD("-period"),
    ALGORITHM("-alg"),
    OUTPUT("-output");

    private final String option;

    public static CommandOption fromOption(String optionValue) {
        for (CommandOption option : values()) {
            if (option.getOption().equals(optionValue)) {
                return option;
            }
        }
        throw new IllegalArgumentException("Недопустимое значение для CommandOption: " + optionValue);
    }
}