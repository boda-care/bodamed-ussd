package com.bodamed.ussd.comands;

import com.bodamed.ussd.api.UserApi;
import com.bodamed.ussd.domain.user.User;
import spark.Session;

public class SetPinCommand extends Command {
    private String message;
    private String password;

    public SetPinCommand(Session session) {
        super(session);
        message = "CON Set Your New Pin";
        session.attribute("message", message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Command handle(String choice) {
        try {
            if(password == null) {
                this.password = choice;
                this.message = "CON Confirm Your Pin";
                session.attribute("message", message);
            } else {
                if(choice.equals(password)) { // OK
                    // Update user pin
                    User sessionUser = session.attribute("user");
                    UserApi.get().resetPin(sessionUser, choice); // Update pin
                    return new LoginCommand(session);
                } else {
                    this.message = "END Pins do not match";
                    session.attribute("message", message);
                }
            }
        } catch (Exception ex) {
            this.message = "END An error occurred kindly contact support.\nThank you for Choosing BodaMax";
            session.attribute("message", message);
        }
        return this;
    }
}
