package com.bodamed.ussd.domain.beneficiary;
import com.bodamed.ussd.domain.finance.Balance;

/**
 * @author Collins Magondu
 */
public class InsurancePremium {
    private long id;
    private Type type;
    private double amount;
    private Balance.Currency currency;

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCurrency(Balance.Currency currency) {
        this.currency = currency;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public Balance.Currency getCurrency() {
        return currency;
    }

    public long getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        DAILY,
        WEEKLY,
        MONTHLY,
    }
}
