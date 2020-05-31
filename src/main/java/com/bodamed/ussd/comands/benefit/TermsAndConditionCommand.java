package com.bodamed.ussd.comands.benefit;

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
            message = "CON 1 to  accepted terms and conditions";
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
            session.attribute("message", "END Successfully accepted Terms an conditions");
            return this;
        }
        return new MenuCommand(session);
    }
}
