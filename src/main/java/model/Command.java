package model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Command {
    private CurrencyName currencyName;
    private DateInterval interval;
}


