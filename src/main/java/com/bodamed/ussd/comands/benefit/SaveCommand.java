package com.bodamed.ussd.comands.benefit;

import com.bodamed.ussd.api.FinanceApi;
import com.bodamed.ussd.comands.Command;
import com.bodamed.ussd.domain.beneficiary.BenefitAccount;
import com.bodamed.ussd.domain.beneficiary.Premium;
import com.bodamed.ussd.domain.finance.TransactionType;
import com.bodamed.ussd.domain.user.User;
import com.bodamed.ussd.util.LipaMpesaDTO;
import com.google.gson.Gson;
import spark.Session;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class SaveCommand extends Command {
    private String message;
    private List<BenefitAccount> accounts;
    SaveCommand(Session session, List<BenefitAccount> accounts) {
        super(session);
        this.accounts = accounts;
        StringBuilder builder = new StringBuilder("CON The premium payable today is KES " + calculateDailyPremium(accounts));
        builder.append(String.format(Locale.ENGLISH, "\n\n%d. %s %.0f\n", 1, "Pay premium @KES", calculateDailyPremium(accounts)));
        builder.append(String.format(Locale.ENGLISH, "%d. %s %.0f\n", 2, "Pay full premium @KES", calculateActivationPremium(accounts)));
        builder.append("3. Pay Another Premium Amount\n\n0. Back");

        message = builder.toString();
        session.attribute("message", message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Command handle(String choice) {
        try {
            switch (choice) {
                case "1":
                    save(Double.toString(calculateDailyPremium(this.accounts)));
                    break;
                case "2":
                    save(Double.toString(calculateActivationPremium(this.accounts)));
                    break;
                case "3":
                    return new SaveAnotherAmountCommand(session);
                default:
                    message = "END Invalid Menu Choice";
                    session.attribute("message", message);
                    break;
            }
        }catch (Exception ex) {
            message = "END Unsuccessful Request";
            session.attribute("message", message);
        }
        return this;
    }

    /**
     * Save amount directly
     * @param amount
     */
    private void save(String amount) {
        LipaMpesaDTO lipaMpesaDTO = new LipaMpesaDTO();
        final User user = session.attribute("user");
        lipaMpesaDTO.setUserId(user.getId());
        lipaMpesaDTO.setAmount(amount);
        lipaMpesaDTO.setPhoneNumber(session.attribute("phoneNumber"));
        lipaMpesaDTO.setTransactionType(TransactionType.SAVINGS);
        message = "END Thank You For Choosing Boda Care";
        session.attribute("message", message);
        FinanceApi.get().save(lipaMpesaDTO);
    }

    private double calculateDailyPremium(List<BenefitAccount> benefitAccounts) {
        List<Premium> dailyPremiums = benefitAccounts.stream()
                .filter(BenefitAccount::canPayPremium)
                .map(BenefitAccount::getPremiums)
                .map(premiums -> premiums.stream().filter(premium -> premium.getType() == Premium.Type.DAILY).findFirst().orElse(null)).collect(Collectors.toList());
        double premiumAmount = 0;
        for(Premium premium: dailyPremiums) {
            premiumAmount+= premium.getAmount().getAmount();
        }
        return premiumAmount;
    }

    private double calculateActivationPremium(List<BenefitAccount> benefitAccounts) {
        List<Premium> dailyPremiums = benefitAccounts.stream()
                .filter(BenefitAccount::canPayPremium)
                .map(BenefitAccount::getPremiums)
                .map(premiums -> premiums.stream().filter(premium -> premium.getType() == Premium.Type.ACTIVATION).findFirst().orElse(null)).collect(Collectors.toList());
        double premiumAmount = 0;
        for(Premium premium: dailyPremiums) {
            premiumAmount+=premium.getAmount().getAmount();
        }
        return premiumAmount;
    }

    public static class SaveAnotherAmountCommand extends  Command{
        private String message;
        SaveAnotherAmountCommand(Session session) {
            super(session);
            message = "CON Pay any other premium amount\n\n0. Back";
            session.attribute("message", message);
        }

        @Override
        public String getMessage() {
            return this.message;
        }

        private void save(String amount) {
            LipaMpesaDTO lipaMpesaDTO = new LipaMpesaDTO();
            final User user = session.attribute("user");
            lipaMpesaDTO.setUserId(user.getId());
            lipaMpesaDTO.setAmount(amount);
            lipaMpesaDTO.setPhoneNumber(session.attribute("phoneNumber"));
            lipaMpesaDTO.setTransactionType(TransactionType.SAVINGS);
            message = "END Thank You For Choosing Boda Care";
            session.attribute("message", message);
            FinanceApi.get().save(lipaMpesaDTO);
        }

        @Override
        public Command handle(String choice) {
            int amount = Integer.parseInt(choice);
            if(amount < 10) {
                message = "CON Premium should be above Ksh 10\n\n0. Back";
                session.attribute("message", message);
            } else {
               save(choice); // Save amount selected
            }
            return this;
        }
    }
}
