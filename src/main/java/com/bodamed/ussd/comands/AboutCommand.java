package com.bodamed.ussd.comands;

import spark.Session;

public class AboutCommand extends Command {
    private String message;
    AboutCommand(Session session) {
        super(session);
        message = "END Boda Med is a service created for the Boda Boda Riders to help them insurer themselves and their motorcycles\n\n";
        session.attribute("message",message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Command handle(String choice) {
        return new MenuCommand(session);
    }
}
