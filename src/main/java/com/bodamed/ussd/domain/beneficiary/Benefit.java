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

    public boolean isNHIF() {
        return programType == Type.NHIF;
    }

    public boolean isMicroInsurance() {
        return programType == Type.MICRO_INSURANCE;
    }

    public boolean isInsurance(){
        return programType == Type.NHIF || programType == Type.MICRO_INSURANCE || programType == Type.THIRD_PARTY_INSURANCE;
    }

    public List<InsurancePackage> getInsurancePackages() {
        return insurancePackages;
    }

    public enum Type{
        SAVINGS,
        MICRO_INSURANCE,
        THIRD_PARTY_INSURANCE,
        NHIF
    }
}
