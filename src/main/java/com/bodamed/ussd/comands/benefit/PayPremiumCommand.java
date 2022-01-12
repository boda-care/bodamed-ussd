package com.bodamed.ussd.comands.benefit;

import com.bodamed.ussd.api.BenefitApi;
import com.bodamed.ussd.comands.Command;
import com.bodamed.ussd.domain.beneficiary.BenefitAccount;
import com.bodamed.ussd.domain.beneficiary.Premium;
import com.bodamed.ussd.domain.user.User;
import com.google.gson.Gson;
import spark.Session;

import java.util.List;
import java.util.Locale;

public class PayPremiumCommand extends Command {
    private BenefitAccount benefitAccount;
    private String message;
    private List<Premium> premiums;

    PayPremiumCommand(Session session, BenefitAccount benefitAccount) {
        super(session);
        this.benefitAccount = benefitAccount;

        StringBuilder builder = new StringBuilder();
        this.premiums = this.benefitAccount.getPremiums();
        int counter = 1;
        for(final Premium premium : premiums) {
            builder.append(counter);
            builder.append(". ");
            builder.append(String.format(Locale.ENGLISH, "%s %s %.0f",premium.getType(),
                    premium.getAmount().getCurrency(), premium.getAmount().getAmount()));
            builder.append("\n");
            counter++;
        }
        builder.append("\n");
        builder.append("0. Back");
        this.message = builder.toString();
        session.attribute("message", "CON " + message); // Message
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Command handle(String choice) {
        try {
            final int intChoice = Integer.parseInt(choice);
            final Premium premium = premiums.get(intChoice - 1);

            User user = session.attribute("user");
            BenefitAccount benefitAccount = BenefitApi.get().payPremium(user.getId(), premium);

            System.out.println(new Gson().toJson(benefitAccount));

            this.message = "END " + "You have successfully paid premium for " + benefitAccount.getBenefit().getName();
            session.attribute("message", this.message); // Message

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            this.message = "END " + "An error occurred paying for  " + benefitAccount.getBenefit().getName();

            session.attribute("message", this.message); // Message
        }
        return this;
    }
}
