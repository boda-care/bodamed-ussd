package com.bodamed.ussd.comands;

import spark.Session;

public abstract class Command{
    public Session session;
    public Command(Session session){
        this.session = session;
    }

    public abstract String getMessage();

    Session getSession() {
        return session;
    }

    public abstract Command handle(String choice);
}


