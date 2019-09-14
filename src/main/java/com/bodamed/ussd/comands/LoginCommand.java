package com.bodamed.ussd.comands;

import spark.Session;

public class LoginCommand extends Command {
    private String message;
    private Session session;

    public LoginCommand(Session session) {
        super(session);
        session.attribute("command",this);
        message = "Enter pin to login to BODA MED or 1 to reset pin.";
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
        // Get beneficiary details
        if(true){
            new BeneficiaryCommand(this.session);
        }
    }
}
