package com.bodamed.ussd.domain.claim;

import com.bodamed.ussd.domain.beneficiary.InsuranceCover;

/**
 * @author Collins Magondu
 */
public class ClaimProcessorContract {
    private long id;
    private long claimProcessorId;
    private long insuranceCoverId;
    private InsuranceCover insuranceCover;

    public InsuranceCover getInsuranceCover() {
        return insuranceCover;
    }

    public void setInsuranceCover(InsuranceCover insuranceCover) {
        this.insuranceCover = insuranceCover;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setInsuranceCoverId(long insuranceCoverId) {
        this.insuranceCoverId = insuranceCoverId;
    }

    public void setClaimProcessorId(long claimProcessorId) {
        this.claimProcessorId = claimProcessorId;
    }

    public long getId() {
        return id;
    }

    public long getInsuranceCoverId() {
        return insuranceCoverId;
    }

    public long getClaimProcessorId() {
        return claimProcessorId;
    }
}
