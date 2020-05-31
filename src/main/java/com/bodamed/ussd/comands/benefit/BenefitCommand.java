package com.bodamed.ussd.comands.benefit;

import com.bodamed.ussd.comands.Command;
import com.bodamed.ussd.domain.beneficiary.BenefitAccount;
import spark.Session;

public class BenefitCommand extends Command {
    private String message;
    private BenefitAccount account;
    private long amount;
    BenefitCommand(Session session, BenefitAccount benefitAccount) {
        super(session);
        this.account = benefitAccount;
        message = "CON " + benefitAccount.getBenefit().getName() +  "\n\n" + "1. Pay Premium\n" +
                "2. Check Status\n" +
                "3. Terms and Conditions\n\n" +
                "0. Back";
        session.attribute("message", this.message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Command handle(String choice) {
        final String message = session.attribute("message");
        if(message.contains("Enter Amount")) {
           try {
               this.amount =  Integer.parseInt(choice);
               session.attribute("message", "END Thank you for choosing Boda Med");
           } catch (Exception ex) {
               session.attribute("message","END " + ex.getMessage());
           }
        } else if(choice.equals("1")) {
            session.attribute("message","CON Enter Amount");
        } else if (choice.equals("2")){
            session.attribute("message", "END Your account status is " + account.getStatus());
        } else if (choice.equals("3")) {
            return new TermsAndConditionCommand(session, account);
        }
        return this;
    }
}
