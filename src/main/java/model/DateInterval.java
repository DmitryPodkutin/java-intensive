package model;

public enum DateInterval {

    TOMORROW("tomorrow"),
    WEEK("week");

    private final String dateInterval;

    DateInterval(String currencyName) {
        this.dateInterval = currencyName;
    }

    public String getCurrencyName() {
        return dateInterval;
    }
}