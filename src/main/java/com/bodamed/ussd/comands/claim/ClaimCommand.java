package com.bodamed.ussd.comands.claim;

import com.bodamed.ussd.api.ClaimApi;
import com.bodamed.ussd.comands.Command;
import com.bodamed.ussd.domain.beneficiary.BenefitAccount;
import com.bodamed.ussd.domain.claim.Claim;
import com.bodamed.ussd.domain.claim.ClaimProcessorContract;
import com.bodamed.ussd.domain.user.User;
import spark.Session;

import java.util.List;

public class ClaimCommand extends Command {
    private String message;
    private BenefitAccount benefitAccount;
    private List<ClaimProcessorContract> claimProcessorContracts;
    public ClaimCommand(Session session, BenefitAccount benefitAccount) {
        super(session);
        this.benefitAccount = benefitAccount;
        this.message = "CON Enter claim creator code \n\n0. Back";
        session.attribute("message", this.message);
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public Command handle(String choice) {
        try {
            if(claimProcessorContracts == null) {
                claimProcessorContracts = ClaimApi.get().getCommonCovers(choice, this.benefitAccount.getId());
                if (claimProcessorContracts == null ){
                    session.attribute("message", "END Make sure you are entering the correct code.\n\n");
                } else if(claimProcessorContracts.size() == 0) {
                    session.attribute("message", "END No Common Covers For the User and Claim Processor\n\n");
                } else if (claimProcessorContracts.size() == 1) {
                    User user = session.attribute("user");
                    Claim claim = new Claim(user.getBeneficiary().getFirstName().concat(user.getBeneficiary().getLastName()),
                            claimProcessorContracts.get(0).getInsuranceCoverId(), claimProcessorContracts.get(0).getClaimProcessorId(),
                            benefitAccount.getId(), user.getBeneficiary().getId());
                    createClaim(claim);
                } else {
                    StringBuilder message = new StringBuilder("CON ");
                    int counter = 1;
                    for(ClaimProcessorContract contract: claimProcessorContracts) {
                        message.append(counter)
                                .append(". ")
                                .append(contract.getInsuranceCover().getName())
                                .append("\n");
                        counter++;
                    }
                    message.append("\n0. Back");
                    session.attribute("message", message.toString());
                }
            } else {
                User user = session.attribute("user");
                Claim claim = new Claim(user.getBeneficiary().getFirstName().concat(user.getBeneficiary().getLastName()),
                        claimProcessorContracts.get(Integer.parseInt(choice) - 1).getInsuranceCoverId(), claimProcessorContracts.get(Integer.parseInt(choice) - 1).getClaimProcessorId(),
                        benefitAccount.getId(), user.getBeneficiary().getId());
                createClaim(claim);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            session.attribute("message", "END Sorry an error occurred." + ex.getMessage());
        }
        return this;
    }

    private void createClaim(Claim claim) {
        Claim createdClaim = ClaimApi.get().createClaim(claim);
        if(createdClaim != null) {
            session.attribute("message", "END Successfully Created Claim\n\n");
        } else  {
            session.attribute("message", "END An error occurred\n\n");
        }
    }
}
