package com.bodamed.ussd.comands;

import com.bodamed.ussd.api.BenefitApi;
import com.bodamed.ussd.domain.beneficiary.Beneficiary;
import com.bodamed.ussd.domain.beneficiary.Benefit;
import com.bodamed.ussd.domain.beneficiary.Relationship;
import com.bodamed.ussd.domain.user.Contact;
import com.bodamed.ussd.util.RegisterDTO;
import spark.Session;

import java.time.LocalDate;

public class RegisterCommand extends Command {
    private String message;
    private String firstName;
    private String secondName;
    private String lastName;
    private String password;
    private String dateOfBirth;
    private boolean isRegistering = false;
    private Benefit benefit;
    private String idNumber;
    private String nhIfNumber;
    private Beneficiary.Gender gender;

    public RegisterCommand(Session session) {
        super(session);
        this.message = "CON 1 to Register for Boda Med";
        session.attribute("message",message);
        benefit = BenefitApi.get().getBenefitByName("Boda Med");
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public Command handle(String choice) {
        try {
            if(choice.equals("1") && firstName == null) {
                isRegistering = true;
                session.attribute("message","CON Enter first name");
            } else if (!choice.equals("1") && !isRegistering){
                session.attribute("message","END Wrong input");
            } else if (secondName == null && choice.equals("1")) {
                secondName = "";
                session.attribute("message","CON Enter last name");
            } else  if (firstName == null) {
                this.firstName = choice;
                session.attribute("message","CON Enter second name or 1 to skip");
            } else if (secondName == null) {
                this.secondName = choice;
                session.attribute("message","CON Enter last name");
            } else if (lastName == null) {
                this.lastName = choice;
                session.attribute("message","CON Enter your id number");
            } else if (idNumber == null) {
                this.idNumber = choice;
                session.attribute("message","CON Enter NHIF number");
            } else if (nhIfNumber == null) {
                this.nhIfNumber = choice;
                session.attribute("message","CON Enter gender\n\n 1. Male\n 2. Female\n");
            } else if (gender == null) {
                this.gender = Beneficiary.Gender.MALE;
                if (choice.equals("2")) {
                    this.gender = Beneficiary.Gender.FEMALE;
                }
                session.attribute("message","CON Enter date of birth DD MM YYYY");
            }
            else if (dateOfBirth == null) {
                String[] dateArray = choice.split(" ");
                if(dateArray.length == 3) {
                    this.dateOfBirth = LocalDate.of(Integer.parseInt(dateArray[2]),
                            Integer.parseInt(dateArray[1]), Integer.parseInt(dateArray[0])).toString();
                    session.attribute("message","CON Enter password");
                } else  {
                    session.attribute("message","END Wrong Date of birth");
                }
            } else if (password == null) {
                this.password = choice;
                session.attribute("message","CON Confirm password");
            } else {
                if(choice.equals(password)) {
                    //Okay
                    RegisterDTO registerDTO = new RegisterDTO.Builder()
                            .setBenefitId(benefit.getId())
                            .setContact(session.attribute("phoneNumber"))
                            .setContactType(Contact.ContactType.PHONE_NUMBER)
                            .setDateOfBirth(dateOfBirth)
                            .setInsurancePackageId(benefit.getInsurancePackages().get(0).getId())
                            .setFirstName(firstName)
                            .setSecondName(secondName)
                            .setLastName(lastName)
                            .setIdNumber(Integer.parseInt(idNumber))
                            .setNhifNumber(nhIfNumber)
                            .setPassword(password)
                            .setGender(gender)
                            .setRelationship(Relationship.ACCOUNT_HOLDER).build();
                    Beneficiary beneficiary = BenefitApi.get().createBeneficiary(registerDTO);
                    if(beneficiary != null) {
                        session.attribute("message","END Successfully Registered");
                    } else {
                        session.attribute("message","END Unsuccessfully Registered");
                    }
                } else {
                    // Wrong Password
                    session.attribute("message", "END Incorrect password confirmation");
                }
            }
        } catch (Exception ex) {
            session.attribute("message", "END Invalid Registration Details");
        }
        return this;
    }
}
