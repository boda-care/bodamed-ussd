package com.bodamed.ussd.comands.benefit;

import com.bodamed.ussd.api.BenefitApi;
import com.bodamed.ussd.api.FinanceApi;
import com.bodamed.ussd.comands.Command;
import com.bodamed.ussd.comands.claim.ClaimCommand;
import com.bodamed.ussd.domain.beneficiary.BenefitAccount;
import com.bodamed.ussd.domain.beneficiary.InsuranceCoverLimit;
import com.bodamed.ussd.domain.finance.Balance;
import com.bodamed.ussd.domain.user.User;
import spark.Session;

import java.util.ArrayList;
import java.util.List;

public class BenefitCommand extends Command {
    private String message;
    private BenefitAccount account;
    private List<BenefitMenuCommand> commands;
    BenefitCommand(Session session, BenefitAccount benefitAccount) {
        super(session);
        this.account = benefitAccount;
        commands = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        if(account.getBenefit().isInsurance() && !account.isPendingPayment()) {
            builder.append(String.format("CON %s \n\n", benefitAccount.getBenefit().getName()));
            builder.append(String.format("Expiry Date  %s \n", benefitAccount.getExpiryDate()));
        } else {
            builder.append(String.format("CON %s \n", benefitAccount.getBenefit().getName()));
        }

        if(account.getBenefit().isInsurance()) {
            builder.append(((benefitAccount.isDefaultedPayment())  ? String.format("Credit Amount : KSH %.0f \n\n", benefitAccount.getCreditAmount()) : "\n"));
        }

        if(benefitAccount.getBenefit().isSavings()) {
            commands.add(BenefitMenuCommand.SAVE);
            commands.add(BenefitMenuCommand.BALANCE);
            commands.add(BenefitMenuCommand.TERMS_AND_CONDITIONS);
            commands.add(BenefitMenuCommand.CHECK_STATUS);
        } else {
            if(!benefitAccount.getBenefit().isNHIF()){
                commands.add(BenefitMenuCommand.LIMIT);
            }
            if(benefitAccount.getBenefit().isInsurance()) {
                commands.add(BenefitMenuCommand.PAY_PREMIUM);
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
        PAY_PREMIUM {
            @Override
            Command execute(Session session, BenefitAccount account) {
                return new PayPremiumCommand(session, account);
            }

            @Override
            public String toString() {
                return "Pay Premium";
            }
        },
        CLAIM {
            @Override
            Command execute(Session session, BenefitAccount account) {
                if(account.getBenefit().isNHIF()) {
                    session.attribute("message", "END Feature Unavailable");
                } else {
                    if(account.isDefaultedPayment()) {
                        if(account.getBenefit().isInsurance()) {
                            session.attribute("message", "END Your account has expired. Pay premium of KSH " + account.getCreditAmount() + " to claim");
                        }
                    } else if(!account.isActive()) {
                        if(account.getBenefit().isInsurance()) {
                            session.attribute("message", "END Your account is not yet activated");
                        }
                    }
                    else {
                        return new ClaimCommand(session, account);
                    }
                }
                return null;
            }

            @Override
            public String toString() {
                return "Claim";
            }
        },
        SAVE{
            @Override
            Command execute(Session session, BenefitAccount account) {
                return new SaveCommand(session, account);
            }

            @Override
            public String toString() {
                return "Save";
            }
        },
        BALANCE {
            @Override
            Command execute(Session session, BenefitAccount account) {
                if(account.getBenefit().isSavings()) {
                    final Balance balance = FinanceApi.get().getBalance(account.getFinanceId());
                    session.attribute("message", String.format(" END %s : %s %.2f\n", "Savings", balance.getCurrency(), balance.getAmount()));
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
