package com.bodamed.ussd;

import com.bodamed.ussd.comands.Command;
import com.bodamed.ussd.comands.LoginCommand;
import com.bodamed.ussd.comands.RegisterCommand;
import spark.Session;

import static spark.Spark.port;
import static spark.Spark.post;
import redis.clients.jedis.Jedis;

public class USSDApplication {
    private static final int PORT = 5000;

    public static void main(String[]args){
        port(PORT);
        post("/ussd", (req,res)-> {
            Session session = req.session();
            session.maxInactiveInterval(10);
            session.attribute("phoneNumber",req.queryParams("phoneNumber"));
            session.attribute("sessionId",req.queryParams("sessionId"));
            session.attribute("serviceCode", req.queryParams("serviceCode"));
            session.attribute("isUser", false);

            if(req.queryParams("text") != null){
                session.attribute("text", req.queryParams("text"));
                String[] inputs = deconstructInputs(session);
                com.bodamed.ussd.session.Session.get().reconstructSessionFromUser(session, inputs, session.attribute("isUser"));
            }
            else {
                if(session.attribute("isUser")) {
                    new LoginCommand(session);
                } else {
                    new RegisterCommand(session);
                }
            }
            return session.attribute("message");
        });
    }
    private static String[] deconstructInputs(Session session) {
        return ((String) session.attribute("text")).split("\\*");
    }
}
