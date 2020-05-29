package com.bodamed.ussd.comands.benefit;

import com.bodamed.ussd.comands.Command;
import com.bodamed.ussd.comands.MenuCommand;
import spark.Session;

public class BenefitCommand extends Command {
    private String message;

    BenefitCommand(Session session) {
        super(session);
        message = "CON 1. Pay Premium\n" +
                "2. Check Status\n" +
                "3. Terms and Conditions\n\n" +
                "0. Back";
        session.attribute("message",this.message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Command handle(String choice) {
        final String message = session.attribute("message");
        if(message.contains("Enter Amount")) {
            session.attribute("message", "END Thank you for choosing Boda Med");
            return this;
        } else if(choice.equals("1")) {
            session.attribute("message","CON Enter Amount");
            return this;
        } else if (choice.equals("2")){
            session.attribute("message", "END Active");
            return this;
        } else if (choice.equals("3")) {
            return new TermsAndConditionCommand(session);
        }
        return new MenuCommand(session);
    }
}
