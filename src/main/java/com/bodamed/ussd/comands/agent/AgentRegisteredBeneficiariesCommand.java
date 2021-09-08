package com.bodamed.ussd.comands.agent;

import com.bodamed.ussd.api.BenefitApi;
import com.bodamed.ussd.comands.Command;
import com.bodamed.ussd.domain.beneficiary.AgentBeneficiary;
import com.bodamed.ussd.domain.user.User;
import spark.Session;

import java.util.List;

public class AgentRegisteredBeneficiariesCommand extends Command {
    private String message;
    AgentRegisteredBeneficiariesCommand(Session session) {
        super(session);
        User user = session.attribute("user");
        List<AgentBeneficiary> beneficiaries = BenefitApi.get().getAgentRegisteredBeneficiaries(user.getId());
        StringBuilder builder = new StringBuilder();
        builder.append("CON ").append(beneficiaries.size()).append(" Registered Beneficiaries\n\n");
        for(int i = 0; i < beneficiaries.size(); i++ ) {
            final AgentBeneficiary beneficiary = beneficiaries.get(i);
            builder.append(i + 1).append(". ").append(beneficiary.getFirstName())
                    .append(" ").append(beneficiary.getMiddleName()).append(" ")
                    .append(beneficiary.getLastName()).append("\n");
        }
        builder.append("\n");
        builder.append("0. Back");
        message = builder.toString();
        session.attribute("message", message);
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
