package com.bodamed.ussd.comands.benefit;

import com.bodamed.ussd.api.BenefitApi;
import com.bodamed.ussd.api.FinanceApi;
import com.bodamed.ussd.comands.Command;
import com.bodamed.ussd.domain.beneficiary.BenefitAccount;
import com.bodamed.ussd.domain.beneficiary.InsuranceCoverLimit;
import com.bodamed.ussd.domain.beneficiary.Premium;
import com.bodamed.ussd.domain.finance.Balance;
import com.bodamed.ussd.domain.user.User;
import spark.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BenefitCommand extends Command {
    private String message;
    private BenefitAccount account;
    private List<BenefitMenuCommand> commands;
    BenefitCommand(Session session, BenefitAccount benefitAccount) {
        super(session);
        this.account = benefitAccount;
        commands = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("CON %s \n", benefitAccount.getBenefit().getName()));

        if(account.getBenefit().isInsurance() && account.canPayPremium()) {
            // Premium Payable For The Day
            List<Premium> dailyPremiums = benefitAccount.getPremiums().stream()
                    .filter(dailyPremium -> dailyPremium.getType() == Premium.Type.DAILY).collect(Collectors.toList());

            if(!dailyPremiums.isEmpty()) {
                Premium premium = dailyPremiums.get(0);
                builder.append(String.format("%s \n\n", premium.getName()));
            }
        }

        commands.add(BenefitMenuCommand.BALANCE);
        if(benefitAccount.getBenefit().isSavings()) {
            commands.add(BenefitMenuCommand.TERMS_AND_CONDITIONS);
            commands.add(BenefitMenuCommand.CHECK_STATUS);
        } else {
            if(!benefitAccount.getBenefit().isNHIF()){
                commands.add(BenefitMenuCommand.LIMIT);
            }
            commands.add(BenefitMenuCommand.TERMS_AND_CONDITIONS);
            commands.add(BenefitMenuCommand.CHECK_STATUS);
        }

        for (int i = 0; i < commands.size(); i++) {
            builder.append(String.format("%d. %s \n", i + 1, commands.get(i).toString()));
        }


        builder.append("\n0. Back");

        this.message = builder.toString();

        session.attribute("message", this.message);
        if(!benefitAccount.isTermsAndConditionsAccepted()) {
            new TermsAndConditionCommand(session, account);
        }
    }

    @Override
    public String getMessage() {
        return message;
    }

    private enum BenefitMenuCommand{
        BALANCE {
            @Override
            Command execute(Session session, BenefitAccount account) {
                final Balance balance = FinanceApi.get().getBalance(account.getFinanceId());
                if(account.getBenefit().isSavings()) {
                    session.attribute("message", String.format(" END %s : %s %.2f\n", "Savings", balance.getCurrency(), balance.getAmount()));
                } else if(account.getBenefit().isInsurance()){
                    session.attribute("message", String.format(" END %s : %s %.2f\n", "Premium Savings", balance.getCurrency(), balance.getAmount()));
                }
                return  null;
            }

            @Override
            public String toString() {
                return "Balance";
            }
        },
        LIMIT {
            @Override
            Command execute(Session session, BenefitAccount account) {
                if(!account.getBenefit().isNHIF()) {
                    // Private Insurance Account
                    final StringBuilder builder = new StringBuilder();
                    builder.append("END Cover Limits \n\n");
                    User user  = session.attribute("user");
                    final List<InsuranceCoverLimit> coverLimits = BenefitApi.get().getPackageLimits(user.getBeneficiary().getId(), account.getInsurancePackageId());

                    for(InsuranceCoverLimit coverLimit: coverLimits) {
                        builder.append(String.format("%s : %s %.2f\n", coverLimit.getName(), coverLimit.getCurrency(), coverLimit.getBalance()));
                    }
                    session.attribute("message", builder.toString());
                } else {
                    session.attribute("message", "END Feature Unavailable For This Account");
                }
                return null;
            }

            @Override
            public String toString() {
                return "Limits";
            }
        },
        TERMS_AND_CONDITIONS {
            @Override
            Command execute(Session session, BenefitAccount account) {
                return new TermsAndConditionCommand(session, account);
            }

            @Override
            public String toString() {
                return "Terms And Conditions";
            }
        },
        CHECK_STATUS {
            @Override
            Command execute(Session session, BenefitAccount account) {
                if(account.getBenefit().isInsurance()) {
                    String message = "END Your account status is " + account.getStatus();
                    if(!account.isPendingPayment()) {
                        message+="\n. Activation date is " + account.getActivationDate();
                    }
                    session.attribute("message", message);
                } else {
                    session.attribute("message", "END Your account status is " + account.getStatus());
                }
                return null;
            }

            @Override
            public String toString() {
                return "Check Status";
            }
        };
        abstract Command execute(Session session, BenefitAccount account);
    }

    @Override
    public Command handle(String choice) {
        return commands.get(Integer.parseInt(choice) - 1).execute(session, account);
    }
}
