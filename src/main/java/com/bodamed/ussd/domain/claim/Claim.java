package com.bodamed.ussd.domain.claim;

public class Claim {
    private long id;
    private String createdBy;
    private String beneficiaryName;
    private long insuranceCoverId;
    private long claimProcessorId;
    private long benefitAccountId;
    private long beneficiaryId;

    public Claim (String beneficiaryName, long insuranceCoverId, long claimProcessorId, long benefitAccountId, long beneficiaryId) {
        this.beneficiaryName = beneficiaryName;
        this.insuranceCoverId = insuranceCoverId;
        this.claimProcessorId = claimProcessorId;
        this.benefitAccountId = benefitAccountId;
        this.beneficiaryId = beneficiaryId;
    }

    public void setClaimProcessorId(long claimProcessorId) {
        this.claimProcessorId = claimProcessorId;
    }

    public void setInsuranceCoverId(long insuranceCoverId) {
        this.insuranceCoverId = insuranceCoverId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBenefitAccountId(long benefitAccountId) {
        this.benefitAccountId = benefitAccountId;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setBeneficiaryId(long beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public long getClaimProcessorId() {
        return claimProcessorId;
    }

    public long getInsuranceCoverId() {
        return insuranceCoverId;
    }

    public long getId() {
        return id;
    }

    public long getBenefitAccountId() {
        return benefitAccountId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public long getBeneficiaryId() {
        return beneficiaryId;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }
}
