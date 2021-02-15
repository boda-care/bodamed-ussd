package com.bodamed.ussd.comands.benefit;

import com.bodamed.ussd.api.BenefitApi;
import com.bodamed.ussd.comands.Command;
import com.bodamed.ussd.domain.beneficiary.BenefitAccount;
import com.bodamed.ussd.domain.user.User;
import spark.Session;

import java.util.List;
import java.util.stream.Collectors;

public class MyBenefitsCommand extends Command {
    private List<BenefitAccount> accounts;
    private int payPremiumIndex = 0;
    private boolean isAcceptedPremium  = true;
    private String message;
    public MyBenefitsCommand(Session session) {
        super(session);
        try {
            int counter = 1;
            StringBuilder builder = new StringBuilder();
            builder.append("CON ");
            User user = session.attribute("user");
            accounts = BenefitApi.get().getBeneficiaryAccounts(user.getId());
            // Check if the accounts have been accepted terms and conditions
            if(!hasAcceptedTermsAndConditions()) {
                builder.append(String.format("%d. Accept T&Cs\n", counter));
                counter++;
                isAcceptedPremium = false;
            } else {
                if (accounts.size() == 1) {
                    new BenefitCommand(session, accounts.get(0));
                } else if (accounts.size() > 1) {
                    for (BenefitAccount account : accounts) {
                        builder.append(counter);
                        builder.append(". ");
                        builder.append(account.getBenefit().getName());
                        builder.append("\n");
                        counter++;
                    }
                }
            }

            if (this.hasAcceptedTermsAndConditions()) { // Al
                payPremiumIndex = counter;
                builder.append(String.format("%d. Pay Premium\n", counter));
            }
            builder.append("\n0. Back");
            message = builder.toString();
            session.attribute("message", message);
        } catch (Exception ex) {
            session.attribute("message", "END " + ex.getMessage());
        }
    }

    private boolean hasAcceptedTermsAndConditions() {
        // Accounts that have not been accepted for terms and conditions;
        List<BenefitAccount> pendingAccounts = accounts.stream()
                .filter(account -> account.getStatus().equals(BenefitAccount.Status.PENDING))
                .collect(Collectors.toList());
        return pendingAccounts.isEmpty();
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Command handle(String choice) {
        if(!isAcceptedPremium) {
            User user = session.attribute("user");
            BenefitAccount account = BenefitApi.get().acceptTermsAndConditions(user.getId(), accounts.get(0));
            if(account != null && account.getStatus() != null) {
                session.attribute("message", "END T&Cs Accepted. Welcome to Boda Care");
            } else {
                session.attribute("message", "END An error occurred accepting T&Cs");
            }
            return this;
        }
        // TODO Pay Daily Premium
        if(Integer.parseInt(choice) == payPremiumIndex && payPremiumIndex != 0) {
            session.attribute("message", "END Premium Paid");
            return this;
        }
        return new BenefitCommand(session, accounts.get(Integer.parseInt(choice) - 1));
    }
}
