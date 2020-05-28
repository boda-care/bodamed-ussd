package com.bodamed.ussd.comands;

import spark.Session;

public class LoginCommand extends Command {
    private String message;

    public LoginCommand(Session session) {
        super(session);
        message = "CON Enter pin to login to BODA MED or 1 to reset pin.";
        session.attribute("message",message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Command handle(String choice) {
        return new MenuCommand(this.session);
    }
}