package com.bodamed.ussd.domain.beneficiary;

/**
 * @author Colllins Magondu 24/08/2020
 */
public class InsuranceCoverLimit {
    private String name;
    private String currency;
    private double balance;

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }
}
