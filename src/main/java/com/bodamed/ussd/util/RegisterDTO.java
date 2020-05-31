package com.bodamed.ussd.util;

import com.bodamed.ussd.domain.beneficiary.Beneficiary;
import com.bodamed.ussd.domain.beneficiary.Relationship;
import com.bodamed.ussd.domain.user.Contact;

public class RegisterDTO {
    private long benefitId;
    private long insurancePackageId;
    private String firstName;
    private String secondName;
    private String lastName;
    private String contact;
    private Contact.ContactType contactType;
    private String password;
    private String dateOfBirth;
    private Relationship relationship;
    private Beneficiary.Gender gender;
    private long agentId;

    public String getContact() {
        return contact;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Beneficiary.Gender getGender() {
        return gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public Contact.ContactType getContactType() {
        return contactType;
    }

    public String getPassword() {
        return password;
    }

    public String getSecondName() {
        return secondName;
    }


    public static class Builder {
        private RegisterDTO registerDTO;
        public Builder() {
            this.registerDTO = new RegisterDTO();
        }

        public Builder setBenefitId(int benefitId){
            this.registerDTO.benefitId = benefitId;
            return this;
        }
        public Builder setInsurancePackageId(long id){
            this.registerDTO.insurancePackageId = id;
            return this;
        }
        public Builder setFirstName(String firstName){
            this.registerDTO.firstName = firstName;
            return this;
        }
        public Builder setSecondName(String secondName){
            this.registerDTO.secondName = secondName;
            return this;
        }
        public Builder setLastName(String lastName){
            this.registerDTO.lastName = lastName;
            return this;
        }
        public Builder setContact(String contact){
            this.registerDTO.contact = contact;
            return this;
        }
        public Builder setDateOfBirth(String dateOfBirth) {
            this.registerDTO.dateOfBirth = dateOfBirth;
            return this;
        }
        public Builder setContactType(Contact.ContactType type){
            this.registerDTO.contactType = type;
            return this;
        }
        public Builder setPassword(String password){
            this.registerDTO.password = password;
            return this;
        }
        public Builder setRelationship(Relationship relationship){
            this.registerDTO.relationship = relationship;
            return this;
        }
        public Builder setGender(Beneficiary.Gender gender){
            this.registerDTO.gender = gender;
            return this;
        }
        public Builder setAgentId(long agentId){
            this.registerDTO.agentId = agentId;
            return this;
        }

        public RegisterDTO build(){
            return this.registerDTO;
        }
    }
}
