package com.bodamed.ussd.comands;

import com.bodamed.ussd.api.UserApi;
import com.bodamed.ussd.domain.user.Contact;
import com.bodamed.ussd.domain.user.User;
import com.bodamed.ussd.util.LoginDTO;
import spark.Session;

public class LoginCommand extends Command {
    private String message;

    public LoginCommand(Session session) {
        super(session);
        message = "CON Enter pin to login to BODA MED or 1 to reset pin.";
        session.attribute("message",message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Command handle(String choice) {
        if(!choice.equals("1")) {
            LoginDTO loginDTO = new LoginDTO(session.attribute("phoneNumber"), Contact.ContactType.PHONE_NUMBER, choice);
            User user = UserApi.get().login(loginDTO);
            if(user != null) {
                session.attribute("user", user);
                return new MenuCommand(this.session);
            } else {
                session.attribute("message", "END Invalid Password");
                return this;
            }
        } else {
            session.attribute("message", "END feature unavailable");
            return this;
        }
    }
}
