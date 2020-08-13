package com.bodamed.ussd.comands.benefit;

import com.bodamed.ussd.api.BenefitApi;
import com.bodamed.ussd.comands.Command;
import com.bodamed.ussd.comands.MenuCommand;
import com.bodamed.ussd.domain.beneficiary.BenefitAccount;
import spark.Session;

public class TermsAndConditionCommand extends Command {
    private BenefitAccount account;
    private String message;
    TermsAndConditionCommand(Session session, BenefitAccount benefitAccount) {
        super(session);
        this.account = benefitAccount;
        if(benefitAccount.isTermsAndConditionsAccepted()) {
            message = "END You have accepted terms and conditions";
            session.attribute("message", message);
        } else {
            message = "CON Accept terms and conditions \n\n 1. Accept\n99. Cancel";
            session.attribute("message", message);
        }
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Command handle(String choice) {
        if(choice.equals("1")) {
            account = BenefitApi.get().acceptTermsAndConditions(account);
            if(account.getStatus() != null) {
                session.attribute("message", "END Successfully accepted Terms an conditions");
            } else {
                session.attribute("message", "END Unsuccessful Request");
            }
            return this;
        } else if (choice.equals("99")) {
            session.attribute("message", "END Thank you for choosing Boda Med");
            return this;
        }
        return new MenuCommand(session);
    }
}
