package com.bodamed.ussd.comands.benefit;

import com.bodamed.ussd.comands.Command;
import com.bodamed.ussd.domain.beneficiary.BenefitAccount;
import spark.Session;

public class CheckStatusCommand extends Command {
    private String message;
    CheckStatusCommand(Session session, BenefitAccount account) {
        super(session);
        message = "END Your account status is " + account.getStatus();

        if(account.getBenefit().isInsurance() && !account.isPendingPayment()) {
            message+="\n. Activation date is " + account.getActivationDate();
        }

        session.attribute("message", message);
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
