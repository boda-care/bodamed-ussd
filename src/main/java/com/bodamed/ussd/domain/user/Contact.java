package com.bodamed.ussd.domain.user;

/**
 * @author Collins Magondu
 */
public class Contact {
    private int id;
    private ContactType type;
    private String contact;
    private String dateCreated;
    private String dateModified;
    private String createdBy;
    private String modifiedBy;

    public Contact(String contact, ContactType type){
        this.type = type;
        this.contact = contact;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(ContactType type) {
        this.type = type;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getDateModified() {
        return dateModified;
    }

    public int getId() {
        return id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public ContactType getType() {
        return type;
    }

    public String getContact() {
        return contact;
    }

    public enum ContactType {
        EMAIL,
        PHONE_NUMBER
    }
}
