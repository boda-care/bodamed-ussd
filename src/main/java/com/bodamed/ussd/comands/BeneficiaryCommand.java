package com.bodamed.ussd.comands;

import spark.Session;

public class BeneficiaryCommand extends Command {
    private String message;
    private Session session;
    BeneficiaryCommand(Session session) {
        super(session);
        session.attribute("command",this);
        message = "1. Create Claim\n" +
                "2. My Benefits\n" +
                "3. Pay Premium\n" +
                "4. Settings\n" +
                "5. About\n\n" +
                "0. Back";
        session.attribute("message",message);
        this.session = session;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void handle(String choice) {
        super.handle(choice);
    }
}
