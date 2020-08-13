package com.bodamed.ussd.util;

import com.bodamed.ussd.domain.finance.TransactionType;

public class LipaMpesaDTO {
    private String phoneNumber;
    private String amount;
    private String accountId;
    private TransactionType transactionType;

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAmount() {
        return amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }
}
