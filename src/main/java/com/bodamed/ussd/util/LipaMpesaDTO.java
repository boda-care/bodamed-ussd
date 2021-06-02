package com.bodamed.ussd.util;

import com.bodamed.ussd.domain.finance.TransactionType;

public class LipaMpesaDTO {
    private String phoneNumber;
    private String amount;
    private long userId;
    private TransactionType transactionType;

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public long getUserId() {
        return userId;
    }

    public String getAmount() {
        return amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }
}
