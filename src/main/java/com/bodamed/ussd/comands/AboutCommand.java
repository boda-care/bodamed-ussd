package com.bodamed.ussd.comands;

import spark.Session;

public class AboutCommand extends Command {
    private String message;
    AboutCommand(Session session) {
        super(session);
        message = "END Boda Care is a digital Boda Boda insurance cover that insurers boda boda riders and their motorcycles\n\n";
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
