package com.bodamed.ussd.comands;

import com.bodamed.ussd.api.UserApi;
import com.bodamed.ussd.domain.user.User;
import spark.Session;

public class ResetPinCommand extends Command {
    private String message;
    private String pin;
    ResetPinCommand(Session session) {
        super(session);
        message = "CON Enter new pin\n\n 99. Cancel";
        session.attribute("message", message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Command handle(String choice) {
        if(choice.equals("99")) {
            session.attribute("message", "END Thank you for using Boda Med");
        } else {
            if(choice.length() >= 4) {
                // Choice
                if(pin == null) {
                    pin = choice;
                    session.attribute("message", "CON Confirm Pin");
                } else if (choice.equals(pin)) {
                    final User sessionUser = session.attribute("user");
                    final User user = UserApi.get().resetPin(sessionUser, choice);
                    if(user.getId() != 0) {
                        session.attribute("message", "END Successfully Reset Pin. " +
                                "You shall receive a confirmation message. Thank you for using Boda Med");
                    } else {
                        session.attribute("message", "END Sorry an error occurred");
                    }
                } else {
                    session.attribute("message", "END Confirmation pin is incorrect");
                }
            } else {
                session.attribute("message", "END Pin Must Have A Minimum of 4 Characters");
            }
        }
        return this;
    }
}
