package com.bodamed.ussd.comands.benefit;

import com.bodamed.ussd.comands.Command;
import com.bodamed.ussd.domain.beneficiary.BenefitAccount;
import com.bodamed.ussd.domain.beneficiary.Premium;
import spark.Session;

import java.util.List;
import java.util.Locale;

public class PayPremiumCommand extends Command {
    private BenefitAccount benefitAccount;
    private String message;
    private List<Premium> premiums;

    private int counter = 1;

    public PayPremiumCommand(Session session, BenefitAccount benefitAccount) {
        super(session);
        this.benefitAccount = benefitAccount;

        StringBuilder builder = new StringBuilder();
        this.premiums = this.benefitAccount.getPremiums();
        for(final Premium account : premiums) {
            builder.append(counter);
            builder.append(". ");
            builder.append(String.format(Locale.ENGLISH, "%s %s %.1f",account.getType(),
                    account.getAmount().getCurrency(), account.getAmount().getAmount()));
            builder.append("\n");
            counter++;
        }
        this.message = builder.toString();
        session.attribute("message", "CON " + message); // Message
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Command handle(String choice) {
        session.attribute("message", "END Pay Premium"); // Message
        return this;
    }
}
