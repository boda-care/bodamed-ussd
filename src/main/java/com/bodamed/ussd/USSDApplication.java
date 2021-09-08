package com.bodamed.ussd;

import com.bodamed.ussd.api.UserApi;
import com.bodamed.ussd.domain.user.User;
import com.bodamed.ussd.util.Constants;
import spark.Request;
import spark.Response;
import spark.Session;

import static spark.Spark.port;
import static spark.Spark.post;

public class USSDApplication {
    private static final int PORT = Integer.parseInt(BuildConfig.PORT);

    public static void main(String[]args){
        port(PORT);
        post("/ussd", USSDApplication::handle);
    }

    /**
     * @param session Current session
     * @return array of choices preselected by user
     */
    private static String[] deconstructInputs(Session session) {
        return ((String) session.attribute("text")).split("\\*");
    }

    private static Object handle(Request req, Response res) {
        Session session = req.session();
        session.maxInactiveInterval(10);
        session.attribute("phoneNumber", Constants.sanitizePhoneNumber(req.queryParams("phoneNumber")));
        session.attribute("sessionId", req.queryParams("sessionId"));
        session.attribute("serviceCode", req.queryParams("serviceCode"));

        User user = UserApi.get().findUserByPhoneNumber(session.attribute("phoneNumber"));

        session.attribute("user", user);
        if (user == null) {
            session.attribute("isUser", false);
        } else {
            session.attribute("isUser", true);
        }

        session.attribute("text", req.queryParams("text"));
        String[] inputs = deconstructInputs(session);
        com.bodamed.ussd.session.Session.get().reconstructSessionFromUser(session, inputs, session.attribute("isUser"));

        return session.attribute("message");
    }
}
