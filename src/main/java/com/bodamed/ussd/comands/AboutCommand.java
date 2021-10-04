package com.bodamed.ussd.comands;

import spark.Session;

public class AboutCommand extends Command {
    private String message;
    AboutCommand(Session session) {
        super(session);
        message = "CON Boda Care is a digital Boda Boda insurance cover that insures boda boda riders and their motorcycles\n\n0.Back";
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
