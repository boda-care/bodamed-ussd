package com.bodamed.ussd.comands.benefit;

import com.bodamed.ussd.api.BenefitApi;
import com.bodamed.ussd.comands.Command;
import com.bodamed.ussd.domain.beneficiary.BenefitAccount;
import com.bodamed.ussd.domain.beneficiary.InsurancePremium;
import com.bodamed.ussd.domain.finance.Finance;
import spark.Session;

import java.util.List;

public class PayPremiumCommand extends Command {
    private String message;
    private List<InsurancePremium> premiums;
    private BenefitAccount account;
    PayPremiumCommand(Session session, BenefitAccount benefitAccount) {
        super(session);
        this.account = benefitAccount;
        premiums = BenefitApi.get().getInsurancePremiums(benefitAccount.getInsurancePackageId());
        StringBuilder builder = new StringBuilder();
        builder.append("CON ");
        for (int i = 0; i < premiums.size(); i++) {
            builder.append(String.format( "%d. %s (%s %.2f)\n", i+1, premiums.get(i).getType(),
                    premiums.get(i).getCurrency(), premiums.get(i).getAmount()));
        }

        builder.append("\n 0.Back");
        this.message = builder.toString();
        session.attribute("message", message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Command handle(String choice) {
        try {
            int i = Integer.parseInt(choice);
            InsurancePremium premium = premiums.get(i - 1);
            Finance finance = BenefitApi.get().payPremium(account.getId(), premium);
            if(finance.getId() > 0) {
                session.attribute("message", String.format("END You choose %s. Thank you for choosing Boda Med.", premium.getType()));
            } else {
                session.attribute("message", "END Unsuccessful  Request");
            }
        } catch (Exception ex) {
            session.attribute("message", "END Wrong Choice");
        }
        return this;
    }
}
