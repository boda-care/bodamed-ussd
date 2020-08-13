package com.bodamed.ussd.comands.benefit;

import com.bodamed.ussd.api.FinanceApi;
import com.bodamed.ussd.comands.Command;
import com.bodamed.ussd.comands.claim.ClaimCommand;
import com.bodamed.ussd.domain.beneficiary.BenefitAccount;
import com.bodamed.ussd.domain.finance.Balance;
import spark.Session;

public class BenefitCommand extends Command {
    private String message;
    private BenefitAccount account;
    private long amount;
    BenefitCommand(Session session, BenefitAccount benefitAccount) {
        super(session);
        this.account = benefitAccount;
        message = "CON " + benefitAccount.getBenefit().getName() +  "\n\n" + "1. Pay Premium\n" +
                "2. Claim\n" +
                "3. Check Status\n" +
                "4. Save\n" +
                "5. Balance\n" +
                "6. Terms and Conditions\n\n" +
                "0. Back";
        session.attribute("message", this.message);
        if(!benefitAccount.isTermsAndConditionsAccepted()) {
            new TermsAndConditionCommand(session, account);
        }
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Command handle(String choice) {
        switch (choice) {
            case "1":
                return new PayPremiumCommand(session, account);
            case "2":
                return new ClaimCommand(session, account);
            case "3":
                session.attribute("message", "END Your account status is " + account.getStatus());
                break;
            case "4":
                return new SaveCommand(session, account);
            case "5":
                // Balance
                final Balance balance = FinanceApi.get().getBalance(account.getFinanceId());
                session.attribute("message", String.format("END Your balance is %s %.2f. " +
                        "Thank you for choosing Boda Med", balance.getCurrency(), balance.getAmount()));
                break;
            case "6":
                return new TermsAndConditionCommand(session, account);
        }
        return this;
    }
}
