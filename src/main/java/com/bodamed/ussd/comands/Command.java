package com.bodamed.ussd.comands;

import spark.Session;

import java.util.List;

public abstract class Command{
    private Command before;
    public Session session;
    public Command(Session session){
        List<Command> commands = com.bodamed.ussd.session.Session.get().getCommandPath();
        if(!commands.isEmpty()) {
            before = commands.get(commands.size() - 1);
        }
        this.session = session;

        com.bodamed.ussd.session.Session.get().getCommandPath().add(this);
    }

    public abstract String getMessage();

    Session getSession() {
        return session;
    }

    public abstract Command handle(String choice);

    public Command back() {
        return before;
    }
}


