package com.bodamed.ussd.comands;

import spark.Session;

/**
 * @author Collins Magondu Muthinja 27/08/2020
 */
public class FeatureUnavailableCommand  extends Command{
    private String message;

    FeatureUnavailableCommand(Session session) {
        super(session);
        message = "CON Feature Is Currently Not Available\n\n 0.Back";
        session.attribute("message", message);
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
