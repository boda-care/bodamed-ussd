package com.bodamed.ussd.comands.benefit;

import com.bodamed.ussd.api.BenefitApi;
import com.bodamed.ussd.comands.Command;
import com.bodamed.ussd.domain.beneficiary.BenefitAccount;
import com.bodamed.ussd.domain.beneficiary.Premium;
import com.bodamed.ussd.domain.user.User;
import spark.Session;

import java.util.List;
import java.util.stream.Collectors;

public class MyBenefitsCommand extends Command {
    private List<BenefitAccount> accounts;
    private boolean isAcceptedPremium  = true;
    private String message;

    private int saveChoice = 0;

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

            if(isAcceptedPremium) {
                saveChoice = counter;
                builder.append(saveChoice);
                builder.append(". ");
                builder.append("Pay");
            }

            builder.append("\n\n0. Back");
            message = builder.toString();
            session.attribute("message", message);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
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
            BenefitApi.get().acceptTermsAndConditions(user.getId(), accounts.get(0));
            session.attribute("message", "CON T&Cs Accepted. Welcome to Boda Care\n\n0. My Benefits");
            return this;
        } else if (Integer.parseInt(choice) == saveChoice) {
            return new SaveCommand(session, this.accounts);
        }
        return new BenefitCommand(session, accounts.get(Integer.parseInt(choice) - 1));
    }
}
