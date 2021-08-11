package com.bodamed.ussd.comands.benefit;

import com.bodamed.ussd.api.BenefitApi;
import com.bodamed.ussd.comands.Command;
import com.bodamed.ussd.domain.beneficiary.BenefitAccount;
import com.bodamed.ussd.domain.beneficiary.InsuranceCoverLimit;
import com.bodamed.ussd.domain.user.User;
import spark.Session;

import java.util.List;

public class LimitsCommand extends Command {
    private String message;

    LimitsCommand(Session session, BenefitAccount account) {
        super(session);

        if(!account.getBenefit().isNHIF()) {
            // Private Insurance Account
            final StringBuilder builder = new StringBuilder();
            builder.append("END Cover Limits");
            User user  = session.attribute("user");
            final List<InsuranceCoverLimit> coverLimits = BenefitApi.get().getPackageLimits(user.getBeneficiary().getId(), account.getInsurancePackageId());

            for(InsuranceCoverLimit coverLimit: coverLimits) {
                builder.append(String.format("%s : %s %.2f\n", coverLimit.getName(), coverLimit.getCurrency(), coverLimit.getBalance()));
            }
//            builder.append("\n\n0. Back");
            message = builder.toString();
            session.attribute("message", message);

        } else {
            message = "END Feature Unavailable For This Account";
            session.attribute("message", message);
        }
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Command handle(String choice) {
        return this;
    }
}
