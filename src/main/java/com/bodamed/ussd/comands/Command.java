package com.bodamed.ussd.comands;

import spark.Session;

public abstract class Command{
    private Session session;
    Command(Session session){
        this.session = session;
    }

    public abstract String getMessage();

    public Session getSession() {
        return session;
    }

    public void handle(String choice){
        if(session.attribute("isRedirect") != null){
            String text = ((String) session.attribute("text")).concat("*");
            session.attribute("text",text);
        }
    }
}


