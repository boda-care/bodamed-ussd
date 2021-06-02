package com.bodamed.ussd.domain.beneficiary;

import com.bodamed.ussd.domain.finance.Amount;

/**
 * @author Collins Magondu 15/02/2021
 */

public class Premium {
    private long benefitAccountId;
    private String name;
    private Type type;
    private Amount amount;
    private InsurancePremium premium;

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public void setBenefitAccountId(long benefitAccountId) {
        this.benefitAccountId = benefitAccountId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setPremium(InsurancePremium premium) {
        this.premium = premium;
    }

    public long getBenefitAccountId() {
        return benefitAccountId;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public Amount getAmount() {
        return amount;
    }

    public InsurancePremium getPremium() {
        return premium;
    }

    public enum Type {
        DAILY {
            @Override
            public String toString() {
                return "Daily";
            }
        },
        ACTIVATION {
            @Override
            public String toString() {
                return "Activation";
            }
        }
    }
}
