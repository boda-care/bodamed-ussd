package com.bodamed.ussd.comands.benefit;

import com.bodamed.ussd.api.BenefitApi;
import com.bodamed.ussd.api.FinanceApi;
import com.bodamed.ussd.comands.Command;
import com.bodamed.ussd.comands.claim.ClaimCommand;
import com.bodamed.ussd.domain.beneficiary.BenefitAccount;
import com.bodamed.ussd.domain.beneficiary.InsuranceCoverBalance;
import com.bodamed.ussd.domain.finance.Balance;
import com.bodamed.ussd.domain.user.User;
import spark.Session;

import java.util.List;

public class BenefitCommand extends Command {
    private String message;
    private BenefitAccount account;
    BenefitCommand(Session session, BenefitAccount benefitAccount) {
        super(session);
        this.account = benefitAccount;
        message = "CON " + benefitAccount.getBenefit().getName() +  "\n\n" +
                "Expiry Date : " + benefitAccount.getExpiryDate() + "\n" +
                ((benefitAccount.isExpired())  ? String.format("Credit Amount : KSH %.0f", benefitAccount.getCreditAmount()) + "\n" : "") + "\n" +
                "1. Pay Premium\n" +
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
                if(account.isExpired()) {
                    session.attribute("message", "END Your account has expired. Pay premium of KSH " + account.getCreditAmount() + " to claim");
                    break;
                } else {
                    return new ClaimCommand(session, account);
                }
            case "3":
                session.attribute("message", "END Your account status is " + account.getStatus());
                break;
            case "4":
                return new SaveCommand(session, account);
            case "5":
                // Balance
                final Balance balance = FinanceApi.get().getBalance(account.getFinanceId());

                final StringBuilder builder = new StringBuilder();
                User user  = session.attribute("user");
                builder.append(String.format(" END %s : %s %.2f\n", "Savings", balance.getCurrency(), balance.getAmount()));
                final List<InsuranceCoverBalance> coverBalances = BenefitApi.get().getPackageBalance(user.getBeneficiary().getId(), account.getInsurancePackageId());
                for(InsuranceCoverBalance coverBalance: coverBalances) {
                    builder.append(String.format("%s : %s %.2f\n", coverBalance.getName(), coverBalance.getCurrency(), coverBalance.getBalance()));
                }
                session.attribute("message", builder.toString());
                break;
            case "6":
                return new TermsAndConditionCommand(session, account);
        }
        return this;
    }
}
