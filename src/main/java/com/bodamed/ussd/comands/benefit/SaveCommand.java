package com.bodamed.ussd.comands.benefit;

import com.bodamed.ussd.api.FinanceApi;
import com.bodamed.ussd.comands.Command;
import com.bodamed.ussd.domain.beneficiary.BenefitAccount;
import com.bodamed.ussd.domain.finance.TransactionType;
import com.bodamed.ussd.util.LipaMpesaDTO;
import com.google.gson.Gson;
import spark.Session;

public class SaveCommand extends Command {
    private String message;
    private BenefitAccount account;
    SaveCommand(Session session, BenefitAccount benefitAccount) {
        super(session);
        this.account = benefitAccount;
        this.message = "CON Enter Amount \n\n0. Back";
        session.attribute("message", message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Command handle(String choice) {
        try {
            int amount = Integer.parseInt(choice);
            if (amount >= 50 && amount <= 18200){
                LipaMpesaDTO lipaMpesaDTO = new LipaMpesaDTO();
                lipaMpesaDTO.setAccountId(Long.toString(account.getId()));
                lipaMpesaDTO.setAmount(Integer.toString(amount));
                lipaMpesaDTO.setPhoneNumber(session.attribute("phoneNumber"));
                lipaMpesaDTO.setTransactionType(TransactionType.SAVINGS);

                lipaMpesaDTO = FinanceApi.get().initiateSTKPush(lipaMpesaDTO);
                System.out.println(new Gson().toJson(lipaMpesaDTO));
                session.attribute("message", "END STK Push triggered. Thank You For Choosing Boda Med");
            } else {
                session.attribute("message", "END Savings amount has to be between KSH 50 and KSH 18200");
            }

        }catch (Exception ex) {
            session.attribute("message", "END Unsuccessful Request");
        }
        return this;
    }
}
