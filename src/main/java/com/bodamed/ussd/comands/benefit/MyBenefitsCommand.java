package com.bodamed.ussd.comands.benefit;

import com.bodamed.ussd.comands.Command;
import com.bodamed.ussd.comands.MenuCommand;
import spark.Session;

public class MyBenefitsCommand extends Command {
    private String message;
    public MyBenefitsCommand(Session session) {
        super(session);
        message = "CON 1.Boda Med";
        session.attribute("message",message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Command handle(String choice) {
        if(choice.equals("1")) {
            return new BenefitCommand(session);
        }
        return new MenuCommand(session);
    }
}
