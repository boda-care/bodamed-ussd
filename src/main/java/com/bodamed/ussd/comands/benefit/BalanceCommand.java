package com.bodamed.ussd.comands.benefit;

import com.bodamed.ussd.api.FinanceApi;
import com.bodamed.ussd.comands.Command;
import com.bodamed.ussd.domain.beneficiary.BenefitAccount;
import com.bodamed.ussd.domain.finance.Balance;
import spark.Session;

public class BalanceCommand extends Command {
    private String message;
    BalanceCommand(Session session, BenefitAccount account) {
        super(session);

        final Balance balance = FinanceApi.get().getBalance(account.getFinanceId());
        if(account.getBenefit().isSavings()) {
            message = String.format(" END %s : %s %.2f\n", "Savings", balance.getCurrency(), balance.getAmount());
            session.attribute("message", message);
        } else if(account.getBenefit().isInsurance()){
            message = String.format(" END %s : %s %.2f\n", "Premium Savings", balance.getCurrency(), balance.getAmount());
            session.attribute("message", message);
        }
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Command handle(String choice) {
        return this;
    }
}
