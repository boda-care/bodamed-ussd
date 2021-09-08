package com.bodamed.ussd.session;

import com.bodamed.ussd.comands.*;
import com.bodamed.ussd.domain.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Collins Magondu 28/05/2020
 */
public class Session {
    private static Session session = new Session();
    private List<Command> commandPath;
    private Session() {
        commandPath = new ArrayList<>();
    }

    public void reconstructSessionFromUser(spark.Session session, String []inputs, boolean isUser) {
        commandPath = new ArrayList<>();
        Command command;
        if(isUser) {
            User user = session.attribute("user");
            if(user.getPassword() == null) {
                command = new SetPinCommand(session);
            } else {
                command = new LoginCommand(session);
            }
        } else {
            command = new RegisterCommand(session);
        }
        // Pin
        if(inputs[0].length() > 0) {
            for (String input : inputs) {
                if (input.equals("0")) {
                    if (!commandPath.isEmpty()) {
                        final Command beforeCommand = command.back();
                        if (beforeCommand != null && !(beforeCommand instanceof LoginCommand)) {
                            command = beforeCommand;
                            commandPath.add(command);
                            session.attribute("message", beforeCommand.getMessage());
                        }
                    }
                } else if (input.equals("00") && (boolean) session.attribute("isUser")) {
                    command = new MenuCommand(session);
                } else {
                    command = commandPath.get(commandPath.size() - 1).handle(input);
                }
            }
        }
    }

    public List<Command> getCommandPath() {
        return commandPath;
    }

    public static Session get() {
        return session;
    }
}
