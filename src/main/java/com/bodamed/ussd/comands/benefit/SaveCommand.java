package com.bodamed.ussd.comands.benefit;

import com.bodamed.ussd.api.FinanceApi;
import com.bodamed.ussd.comands.Command;
import com.bodamed.ussd.domain.finance.TransactionType;
import com.bodamed.ussd.domain.user.User;
import com.bodamed.ussd.util.LipaMpesaDTO;
import com.google.gson.Gson;
import spark.Session;

public class SaveCommand extends Command {
    private String message;
    SaveCommand(Session session, double premiumDue) {
        super(session);
        this.message = "CON The premium payable today is KES " + premiumDue + "\n\nEnter Amount To Save\n0. Back";
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
            if (amount >= 10){
                LipaMpesaDTO lipaMpesaDTO = new LipaMpesaDTO();
                final User user = session.attribute("user");
                lipaMpesaDTO.setUserId(user.getId());
                lipaMpesaDTO.setAmount(Integer.toString(amount));
                lipaMpesaDTO.setPhoneNumber(session.attribute("phoneNumber"));
                lipaMpesaDTO.setTransactionType(TransactionType.SAVINGS);
                lipaMpesaDTO = FinanceApi.get().save(lipaMpesaDTO);
                System.out.println(new Gson().toJson(lipaMpesaDTO));
                session.attribute("message", "END Thank You For Choosing Boda Care");
            } else {
                session.attribute("message", "END Savings amount has to be above or equals to KSH 10");
            }
        }catch (Exception ex) {
            session.attribute("message", "END Unsuccessful Request");
        }
        return this;
    }
}
