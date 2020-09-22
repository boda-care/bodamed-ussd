package com.bodamed.ussd.comands;

import com.bodamed.ussd.comands.benefit.MyBenefitsCommand;
import spark.Session;

public class MenuCommand extends Command {
    private String message;
    public MenuCommand(Session session) {
        super(session);
        message = "CON 1. My Benefits\n" +
                "2. Settings\n" +
                "3. About\n\n" +
                "0. Back";
        session.attribute("message",message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Command handle(String choice) {
        switch (choice) {
            case "1":
                return new MyBenefitsCommand(session);
            case "2":
                return new FeatureUnavailableCommand(session);
            case "3":
                return new AboutCommand(session);
        }
        return new MenuCommand(session) ;
    }
}
