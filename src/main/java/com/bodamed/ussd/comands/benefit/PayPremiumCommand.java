package com.bodamed.ussd.comands.benefit;

import com.bodamed.ussd.api.BenefitApi;
import com.bodamed.ussd.comands.Command;
import com.bodamed.ussd.domain.beneficiary.BenefitAccount;
import com.bodamed.ussd.domain.beneficiary.InsurancePremium;
import com.bodamed.ussd.domain.finance.Finance;
import com.bodamed.ussd.domain.user.User;
import com.bodamed.ussd.util.AccountPremiumDTO;
import spark.Session;

import java.util.List;

public class PayPremiumCommand extends Command {
    private String message;
    private List<InsurancePremium> premiums;
    private BenefitAccount account;
    private List<BenefitAccount> accounts;
    PayPremiumCommand(Session session, BenefitAccount benefitAccount) {
        super(session);
        this.account = benefitAccount;
        // If account is pending payment fetch activation premiums
        if(benefitAccount.isPendingPayment()) {
            premiums = BenefitApi.get().getInsurancePreActivationPremiums(benefitAccount.getInsurancePackageId());
        } else {
            premiums = BenefitApi.get().getInsurancePremiums(benefitAccount.getInsurancePackageId());
        }

        if(premiums.isEmpty()) {
            this.message = "END You cannot pay premium for you " +  account.getBenefit().getName() + " benefit. Thank you for choosing Boda Med";
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append("CON ");
            int startIndex = 0;
            if(this.account.isDefaultedPayment() && this.account.getBenefit().isInsurance() && !benefitAccount.isPendingPayment()) {
                startIndex = 1;
                builder.append(String.format(" %d. Activate Account (KSH %.2f)\n", startIndex, this.account.getCreditAmount()));
            }
            for (int i = 0; i < premiums.size(); i++) {
                final InsurancePremium premium = premiums.get(i);
                if(premium.getType().equals(InsurancePremium.Type.ACTIVATION_TARGET)) {
                    builder.append(String.format( "%d. %s \n", i + (startIndex + 1), premium.getType()));
                } else {
                    builder.append(String.format( "%d. %s (%s %.2f)\n", i + (startIndex + 1), premium.getType(),
                            premium.getCurrency(), premium.getAmount()));
                }
            }

            builder.append("\n 0.Back");
            this.message = builder.toString();
        }
        session.attribute("message", message);
    }

    /**
     * Pay premium for multiple accounts
     * @param session The current session
     */
    PayPremiumCommand(Session session) {
        super(session);
        // TODO Fetch all premiums payable
        User user = session.attribute("user");


    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Command handle(String choice) {
        try {
            if(account.isDefaultedPayment() && choice.equals("1")) {
                AccountPremiumDTO accountPremiumDTO = new AccountPremiumDTO.Builder()
                        .setBenefitAccountId(account.getId())
                        .setAmount(account.getCreditAmount())
                        .setDays((int) (account.getCreditAmount() / 50))
                        .build();
                Finance finance = BenefitApi.get().payForExpiredAccount(accountPremiumDTO);
                if(finance.getId() > 0) {
                    session.attribute("message", "END Successfully Updated Account");
                } else {
                    session.attribute("message", "END You have insufficient funds");
                }
                return this;
            }
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
