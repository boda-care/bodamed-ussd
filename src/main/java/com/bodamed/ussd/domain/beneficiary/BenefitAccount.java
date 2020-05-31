package com.bodamed.ussd.domain.beneficiary;

import java.util.List;

/**
 * @author Collins Magondu
 */
public class BenefitAccount {
    private long id;
    private long financeId;
    private List<BenefitAccountEvent> events;
    private Benefit benefit;
    private boolean termsAndConditionsAccepted;
    private Status status;

    public void setId(long id) {
        this.id = id;
    }

    public void setEvents(List<BenefitAccountEvent> events) {
        this.events = events;
    }

    public void setFinanceId(long financeId) {
        this.financeId = financeId;
    }

    public long getId() {
        return id;
    }

    public List<BenefitAccountEvent> getEvents() {
        return events;
    }

    public long getFinanceId() {
        return financeId;
    }

    public void setBenefit(Benefit benefit) {
        this.benefit = benefit;
    }

    public void setTermsAndConditionsAccepted(boolean termsAndConditionsAccepted) {
        this.termsAndConditionsAccepted = termsAndConditionsAccepted;
    }

    public boolean isTermsAndConditionsAccepted() {
        return termsAndConditionsAccepted;
    }

    public Benefit getBenefit() {
        return benefit;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public enum Status {
        ACTIVE,
        CLOSED,
        SUSPENDED,
        PENDING
    }
}
