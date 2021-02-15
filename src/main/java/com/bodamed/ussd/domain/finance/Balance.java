package com.bodamed.ussd.domain.finance;

public class Balance {
    private double amount;
    private  Currency currency;

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getAmount() {
        return amount;
    }

    public enum Currency {
        KES,
        NONE {
            @Override
            public String toString() {
                return "";
            }
        }
    }
}
