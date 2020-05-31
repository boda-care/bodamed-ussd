package com.bodamed.ussd.util;

import com.bodamed.ussd.domain.user.Contact;

/**
 * @author Collins Magondu
 */
public class LoginDTO {
    private String contact;
    private Contact.ContactType contactType;
    private String password;
    public LoginDTO (String contact, Contact.ContactType contactType, String password) {
        this.password = password;
        this.contact = contact;
        this.contactType = contactType;
    }
    public String getPassword() {
        return password;
    }

    public Contact.ContactType getContactType() {
        return contactType;
    }

    public String getContact() {
        return contact;
    }
}
