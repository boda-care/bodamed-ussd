package com.bodamed.ussd.domain.beneficiary;

/**
 * @author Collins Magondu
 */
public class InsurancePackage {
    private Benefit benefit;
    private String name;
    private long id;

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBenefit(Benefit benefit) {
        this.benefit = benefit;
    }

    public String getName() {
        return name;
    }

    public Benefit getBenefit() {
        return benefit;
    }

    public long getId() {
        return id;
    }
}
