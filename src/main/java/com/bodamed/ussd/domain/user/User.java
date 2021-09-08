package com.bodamed.ussd.domain.user;

import com.bodamed.ussd.domain.beneficiary.Beneficiary;

/**
 * @author Collins Magondu
 */
public class User {
    private long id;
    private String password;
    private Type type;
    private Beneficiary beneficiary;

    public void setBeneficiary(Beneficiary beneficiary) {
        this.beneficiary = beneficiary;
    }

    public Beneficiary getBeneficiary() {
        return beneficiary;
    }

    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAgent() {
        return type.equals(Type.AGENT);
    }

    public Type getType() {
        return type;
    }

    enum Type {
        USER,
        AGENT
    }
}
