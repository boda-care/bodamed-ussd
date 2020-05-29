package com.bodamed.ussd.comands.benefit;

import com.bodamed.ussd.comands.Command;
import com.bodamed.ussd.comands.MenuCommand;
import spark.Session;

public class TermsAndConditionCommand extends Command {
    private String message;
    TermsAndConditionCommand(Session session) {
        super(session);
        boolean isAcceptedTermsAndCondition = false;
        if(isAcceptedTermsAndCondition) {
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
