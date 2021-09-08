package com.bodamed.ussd.domain.beneficiary;

public class AgentBeneficiary {
    private int userId;
    private String salutation;
    private String firstName;
    private String middleName;
    private String lastName;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSalutation() {
        return salutation;
    }

    public int getUserId() {
        return userId;
    }

    public String getMiddleName() {
        return middleName;
    }
}
