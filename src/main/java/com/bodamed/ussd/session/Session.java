package com.bodamed.ussd.session;

import com.bodamed.ussd.comands.Command;
import com.bodamed.ussd.comands.LoginCommand;
import com.bodamed.ussd.comands.MenuCommand;
import com.bodamed.ussd.comands.RegisterCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Collins Magondu 28/05/2020
 */
public class Session {
    private static Session session = new Session();
    private Map<Integer, Map<String, Command>> commandPath;
    private Session() {
    }

    public void reconstructSessionFromUser(spark.Session session, String []inputs, boolean isUser) {
        commandPath = new HashMap<>();
        Command command;
        command = (isUser) ? new LoginCommand(session) : new RegisterCommand(session);
        // Pin
        if(inputs[0].length() > 0) {
            for (int i = 0; i < inputs.length ; i++) {
                if(inputs[i].equals("0")) {
                    if(!commandPath.isEmpty()) {
                        final Command beforeCommand = commandPath.get(i-1).get(inputs[i-1]).handle(inputs[i-1]);
                        if(!(beforeCommand instanceof LoginCommand)) {
                            command = beforeCommand;
                        }
                    }
                } else if (inputs[i].equals("00") && (boolean) session.attribute("isUser")){
                    command = new MenuCommand(session);
                }
                else {
                    command = command.handle(inputs[i]);
                }
                final Map<String, Command> map = new HashMap<>();
                map.put(inputs[i], command);
                commandPath.put(i, map);
            }
        }
    }

    public static Session get() {
        return session;
    }
}
