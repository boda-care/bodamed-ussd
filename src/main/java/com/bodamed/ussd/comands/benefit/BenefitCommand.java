package com.bodamed.ussd.comands.benefit;

import com.bodamed.ussd.comands.Command;
import com.bodamed.ussd.domain.beneficiary.BenefitAccount;
import com.bodamed.ussd.domain.beneficiary.Premium;
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
                return new BalanceCommand(session, account);
            }

            @Override
            public String toString() {
                return "Balance";
            }
        },
        LIMIT {
            @Override
            Command execute(Session session, BenefitAccount account) {
                return new LimitsCommand(session, account);
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
                return new CheckStatusCommand(session, account);
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
