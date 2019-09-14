package com.bodamed.ussd;

import spark.Session;

import static spark.Spark.get;
import static spark.Spark.port;

public class USSDApplication {
    public static final int PORT = 5000;

    public static void main(String[]args){
        port(PORT);
        get("/ussd", (req,res)-> {
            Session session = req.session(true);
            session.invalidate();
            return session.id();
        });
    }
}
