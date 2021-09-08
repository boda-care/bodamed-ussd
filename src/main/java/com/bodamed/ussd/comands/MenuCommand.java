package com.bodamed.ussd.comands;

import com.bodamed.ussd.comands.agent.AgentCommand;
import com.bodamed.ussd.comands.benefit.MyBenefitsCommand;
import com.bodamed.ussd.domain.user.User;
import spark.Session;

public class MenuCommand extends Command {
    private String message;
    private boolean isAgent;
    public MenuCommand(Session session) {
        super(session);
        User user = session.attribute("user");
        this.isAgent = user.isAgent();

        message = "CON 1. My Benefits\n";

        if(this.isAgent) { // Add register command
            message +=  "2. Agent \n3. About";
        } else {
            message +=  "2 About";
        }

        session.attribute("message", message);
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
                if (this.isAgent) {
                    return new AgentCommand(session);
                } else {
                    return new AboutCommand(session);
                }
            case "3":  // About Command
                return new AboutCommand(session);
        }
        return this ;
    }
}
