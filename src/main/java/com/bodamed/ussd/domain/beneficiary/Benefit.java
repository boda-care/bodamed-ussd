package com.bodamed.ussd.domain.beneficiary;

import java.util.List;

/**
 * @author Collins Magondu
 */

public class Benefit {
    private String name;
    private int id;
    private List<InsurancePackage> insurancePackages;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInsurancePackages(List<InsurancePackage> insurancePackages) {
        this.insurancePackages = insurancePackages;
    }


    public List<InsurancePackage> getInsurancePackages() {
        return insurancePackages;
    }
}
