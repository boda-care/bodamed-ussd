package com.bodamed.ussd.domain.beneficiary;

import java.util.List;

/**
 * @author Collins Magondu
 */

public class Benefit {
    private String name;
    private Type programType;
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

    public Type getProgramType() {
        return programType;
    }

    public void setProgramType(Type programType) {
        this.programType = programType;
    }

    public boolean isSavings(){
        return programType == Type.SAVINGS;
    }

    public boolean isPublicInsurance() {
        return programType == Type.PUBLIC_INSURANCE;
    }

    public boolean isPrivateInsurance() {
        return programType == Type.PRIVATE_INSURANCE;
    }

    public boolean isInsurance(){
        return programType == Type.PUBLIC_INSURANCE || programType == Type.PRIVATE_INSURANCE;
    }

    public List<InsurancePackage> getInsurancePackages() {
        return insurancePackages;
    }

    public enum Type{
        SAVINGS,
        PRIVATE_INSURANCE,
        PUBLIC_INSURANCE
    }
}
