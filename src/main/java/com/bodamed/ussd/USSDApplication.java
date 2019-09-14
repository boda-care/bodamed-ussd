package com.bodamed.ussd;

import com.bodamed.ussd.comands.Command;
import com.bodamed.ussd.comands.LoginCommand;
import spark.Session;


import static spark.Spark.port;
import static spark.Spark.post;

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
            session.attribute("isRedirect", req.queryParams("isRedirect"));

            if(req.queryParams("text") == null && session.attribute("isRedirect") != null){
                session.attribute("text","");
            }
            else {
                session.attribute("text",req.queryParams("text"));
            }
            if(session.attribute("command") != null){
                String[] inputs = deconstructInputs(session);
                    ((Command) session.attribute("command")).handle(inputs[inputs.length -1]);
            }
            else{
                new LoginCommand(session);
            }
            return session.attribute("message");
        });
    }
    private static String[] deconstructInputs(Session session){
        return  ((String) session.attribute("text")).split("\\*");
    }
}
