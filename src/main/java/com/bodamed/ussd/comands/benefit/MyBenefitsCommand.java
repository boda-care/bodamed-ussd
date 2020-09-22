package com.bodamed.ussd.comands.benefit;

import com.bodamed.ussd.api.BenefitApi;
import com.bodamed.ussd.comands.Command;
import com.bodamed.ussd.domain.beneficiary.BenefitAccount;
import com.bodamed.ussd.domain.user.User;
import spark.Session;

import java.util.List;

public class MyBenefitsCommand extends Command {
    private String message;
    private List<BenefitAccount> accounts;
    public MyBenefitsCommand(Session session) {
        super(session);
        User user = session.attribute("user");
        accounts = BenefitApi.get().getBeneficiaryAccounts(user.getId());
        message = session.attribute("message");
        if(accounts.size() == 1) {
            new BenefitCommand(session, accounts.get(0));
        } else if (accounts.size() > 1) {
            StringBuilder builder = new StringBuilder();
            int counter = 1;
            builder.append("CON ");
            for (BenefitAccount account: accounts) {
                builder.append(counter);
                builder.append(". ");
                builder.append(account.getBenefit().getName());
                builder.append("\n");
                counter++;
            }
            builder.append("\n0. Back");
            message = builder.toString();
        }
        session.attribute("message",  message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Command handle(String choice) {
        return new BenefitCommand(session, accounts.get(Integer.parseInt(choice) - 1));
    }
}
