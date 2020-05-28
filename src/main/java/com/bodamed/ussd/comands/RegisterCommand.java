package com.bodamed.ussd.comands;

import spark.Session;

public class RegisterCommand extends Command {
    private String message;

    public RegisterCommand(Session session) {
        super(session);
        this.message = "CON 1 to Register for Boda Med";
        session.attribute("message",message);
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public Command handle(String choice) {
        if(choice.equals("1")) {
            return new SetPinCommand(session);
        } else {
            session.attribute("message", "END Wrong Input");
            return this;
        }
    }
}
