package com.bodamed.ussd.comands;

import spark.Session;

public class SetPinCommand extends Command{
    private String message;
    SetPinCommand(Session session) {
        super(session);
        this.message = "CON Set New Pin \n\n 99. Cancel";
        session.attribute("message", this.message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Command handle(String choice) {
        final String message = session.attribute("message");
        if(message.contains("Confirm Password")) {
            session.attribute("message", "END Dial *384*4105# to access this service \n\n");
        } else if (choice.equals("99")) {
            return new RegisterCommand(session);
        } else {
            session.attribute("message", "CON Confirm Password \n\n");
        }
        return this;
    }
}
