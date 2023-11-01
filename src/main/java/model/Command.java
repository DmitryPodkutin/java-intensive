package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Command {
    private final RateCommand rateCommand;
    private final DateInterval interval;
}


