package com.bodamed.ussd.domain.beneficiary;

/**
 * @author Collins Magondu
 */
public class Beneficiary {
    private int id;
    private int userId;
    private String salutation;
    private String firstName;
    private String middleName;
    private String lastName;
    private String dateCreated;
    private Gender gender;
    private String dateModified;
    private String dateOfBirth;
    private int agentId;

    public Beneficiary(String firstName, String middleName, String lastName, int agentId, Gender gender) {
        this.agentId = agentId;
        this.gender = gender;
        this.lastName = lastName;
        this.middleName = middleName;
        this.firstName = firstName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getDateModified() {
        return dateModified;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public Gender getGender() {
        return gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public int getAgentId() {
        return agentId;
    }

    public int getUserId() {
        return userId;
    }

    public String getSalutation() {
        return salutation;
    }

    public enum Gender {
        MALE,
        FEMALE
    }
}
