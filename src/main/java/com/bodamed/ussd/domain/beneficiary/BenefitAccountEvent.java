package com.bodamed.ussd.domain.beneficiary;

/**
 * @author Collins Magondu
 */
public class BenefitAccountEvent {
    private long id;
    private boolean done;
    private long benefitAccountId;
    private BenefitAccount benefitAccount;
    private Event event;

    public void setId(long id) {
        this.id = id;
    }

    public void setBenefitAccountId(long benefitAccountId) {
        this.benefitAccountId = benefitAccountId;
    }

    public void setBenefitAccount(BenefitAccount benefitAccount) {
        this.benefitAccount = benefitAccount;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public long getId() {
        return id;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

    public long getBenefitAccountId() {
        return benefitAccountId;
    }

    public BenefitAccount getBenefitAccount() {
        return benefitAccount;
    }

    public boolean isDone() {
        return done;
    }

    public enum Event {
        CREATED,
        ACTIVATED,
        CLOSED,
        TERMS_AND_CONDITIONS_ACCEPTED,
        PREMIUM_PAID,
        SUSPENDED
    }
}
