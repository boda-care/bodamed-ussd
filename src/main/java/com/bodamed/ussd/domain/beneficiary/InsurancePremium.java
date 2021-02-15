package com.bodamed.ussd.domain.beneficiary;
import com.bodamed.ussd.domain.finance.Balance;

/**
 * @author Collins Magondu
 */
public class InsurancePremium {
    private long id;
    private Type type;
    private double amount;
    private long insurancePackageId;
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

    public long getInsurancePackageId() {
        return insurancePackageId;
    }

    public void setInsurancePackageId(long insurancePackageId) {
        this.insurancePackageId = insurancePackageId;
    }

    public enum Type {
        DAILY {
            @Override
            public String toString() {
                return "DAILY";
            }
        },
        WEEKLY {
            @Override
            public String toString() {
                return "WEEKLY";
            }
        },
        MONTHLY {
            @Override
            public String toString() {
                return "MONTHLY";
            }
        },
        DAILY_ACTIVATION {
            @Override
            public String toString() {
                return "DAILY";
            }
        },
        ACTIVATION_TARGET {
            @Override
            public String toString() {
                return "ACTIVATE";
            }
        },
        YEARLY
    }
}
