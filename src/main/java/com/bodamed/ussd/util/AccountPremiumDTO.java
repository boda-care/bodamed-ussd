package com.bodamed.ussd.util;

public class AccountPremiumDTO {
    private int days;
    private double amount;
    private long benefitAccountId;

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setBenefitAccountId(long benefitAccountId) {
        this.benefitAccountId = benefitAccountId;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public double getAmount() {
        return amount;
    }

    public long getBenefitAccountId() {
        return benefitAccountId;
    }

    public int getDays() {
        return days;
    }

    public static class Builder {
        private AccountPremiumDTO accountPremiumDTO;
        public Builder() {
            this.accountPremiumDTO = new AccountPremiumDTO();
        }

        public Builder setDays(int days) {
            this.accountPremiumDTO.days = days;
            return this;
        }

        public Builder setAmount(double amount){
            this.accountPremiumDTO.amount = amount;
            return this;
        }

        public Builder setBenefitAccountId(long benefitAccountId){
            this.accountPremiumDTO.benefitAccountId = benefitAccountId;
            return this;
        }

        public AccountPremiumDTO build(){
            return this.accountPremiumDTO;
        }
    }
}
