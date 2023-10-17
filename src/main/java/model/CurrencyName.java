package model;

public enum CurrencyName {
    EUR("EUR"),
    USD("USD"),
    TRY("TRY");

    private final String currencyName;

    CurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyName() {
        return currencyName;
    }
}