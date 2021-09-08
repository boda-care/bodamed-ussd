package com.bodamed.ussd.comands.agent;

import com.bodamed.ussd.comands.Command;
import spark.Session;

public class AgentCommand extends Command {
    private String message;
    public AgentCommand(Session session) {
        super(session);
        message = "CON 1. Register User\n"
                + "2. My Users\n\n" +
                "0.Back";
        session.attribute("message", message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Command handle(String choice) {
        if(choice.equals("1")) {
            return new AgentRegisterBeneficiaryCommand(session);
        } else if (choice.equals("2")) {
            return new AgentRegisteredBeneficiariesCommand(session);
        }
        return this;
    }
}
