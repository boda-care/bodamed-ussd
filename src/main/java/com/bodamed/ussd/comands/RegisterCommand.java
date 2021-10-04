package com.bodamed.ussd.comands;

import com.bodamed.ussd.api.BenefitApi;
import com.bodamed.ussd.domain.beneficiary.Beneficiary;
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
    private String nhifNumber;
    private boolean isRegistering = false;
    private String idNumber;
    private Beneficiary.Gender gender;

    public RegisterCommand(Session session) {
        super(session);
        this.message = "CON 1 to Register for BODA CARE";
        session.attribute("message",message);
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
                session.attribute("message","CON Enter your nhif number or 1 to skip");
            } else if (nhifNumber == null) {
                this.nhifNumber = choice;
                session.attribute("message","CON Enter your id number");
            } else if (idNumber == null) {
                this.idNumber = choice;
                session.attribute("message","CON Enter gender\n\n 1. Male\n 2. Female\n");
            }  else if (gender == null) {
                this.gender = Beneficiary.Gender.MALE;
                if (choice.equals("2")) {
                    this.gender = Beneficiary.Gender.FEMALE;
                }
                session.attribute("message","CON Enter date of birth DDMMYYYY");
            }
            else if (dateOfBirth == null) {
                try {
                    String[] dateArray = choice.split("");
                    String day = dateArray[0] + dateArray[1];
                    String month = dateArray[2] + dateArray[3];
                    String year = dateArray[4] + dateArray[5] + dateArray[6] + dateArray[7];

                    this.dateOfBirth = LocalDate.of(Integer.parseInt(year),
                            Integer.parseInt(month), Integer.parseInt(day)).toString();
                    session.attribute("message","CON Enter new password");
                } catch (Exception ex) {
                    session.attribute("message","CON Wrong date of birth input. Input date as DD/MM/YYYY");
                    System.out.println(ex.getMessage());
                }
            } else if (password == null) {
                this.password = choice;
                session.attribute("message","CON Confirm password");
            } else {
                if(choice.equals(password)) {
                    //Okay
                    RegisterDTO registerDTO = new RegisterDTO.Builder()
                            .setContact(session.attribute("phoneNumber"))
                            .setContactType(Contact.ContactType.PHONE_NUMBER)
                            .setDateOfBirth(dateOfBirth)
                            .setFirstName(firstName)
                            .setSecondName(secondName)
                            .setLastName(lastName)
                            .setIdNumber(Integer.parseInt(idNumber))
                            .setPassword(password)
                            .setGender(gender)
                            .setRelationship(Relationship.ACCOUNT_HOLDER).build();
                    Beneficiary beneficiary = BenefitApi.get().createBeneficiary(registerDTO);
                    if(beneficiary != null) {
                        // TODO add accept terms and condition on screen
                        session.attribute("message","END You Successfully Registered For BodaCare.\n " +
                                "Dail *483*204# to accept terms and conditions.");
                    } else {
                        session.attribute("message","END Unsuccessfully Registered");
                    }
                } else {
                    // Wrong Password
                    session.attribute("message", "END Incorrect password confirmation");
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            session.attribute("message", "END Invalid Registration Details");
        }
        return this;
    }
}
