package com.bodamed.ussd.comands.agent;

import com.bodamed.ussd.api.BenefitApi;
import com.bodamed.ussd.comands.Command;
import com.bodamed.ussd.domain.beneficiary.Beneficiary;
import com.bodamed.ussd.domain.beneficiary.Relationship;
import com.bodamed.ussd.domain.user.Contact;
import com.bodamed.ussd.domain.user.User;
import com.bodamed.ussd.util.RegisterDTO;
import spark.Session;

import java.time.LocalDate;

public class AgentRegisterBeneficiaryCommand extends Command {
    private String message;
    private String firstName;
    private String secondName;
    private String lastName;
    private String dateOfBirth;
    private String nhifNumber;
    private String idNumber;
    private Beneficiary.Gender gender;
    private User agent;
    private String phoneNumber;

    AgentRegisterBeneficiaryCommand(Session session) {
        super(session);
        this.message = "CON Enter phone number in format 254";
        session.attribute("message", message);
        agent = session.attribute("user");
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public Command handle(String choice) {
        try {
           if (phoneNumber == null) { // Get phone number if its type agent
                if(choice.startsWith("254")) {
                    this.phoneNumber = choice;
                    session.attribute("message","CON Enter first name");
                } else {
                    session.attribute("message","END Number should start with 254");
                }
            } else if (firstName == null) {
                this.firstName = choice;
                session.attribute("message","CON Enter second name or 1 to skip");
            } else if (secondName == null) {
               if(choice.equals("1")) {
                   this.secondName = "";
               } else {
                   this.secondName = choice; // Set second name
               }
                session.attribute("message","CON Enter last name");
            } else if (lastName == null) {
                this.lastName = choice;
                session.attribute("message","CON Enter nhif number or 1 to skip");
            } else if (nhifNumber == null) {
                this.nhifNumber = choice;
                session.attribute("message","CON Enter id number");
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

                    RegisterDTO registerDTO = new RegisterDTO.Builder()
                            .setContact(this.phoneNumber)
                            .setContactType(Contact.ContactType.PHONE_NUMBER)
                            .setDateOfBirth(dateOfBirth)
                            .setFirstName(firstName)
                            .setSecondName(secondName)
                            .setLastName(lastName)
                            .setAgentId(agent.getId())
                            .setIdNumber(Integer.parseInt(idNumber))
                            .setGender(gender)
                            .setRelationship(Relationship.ACCOUNT_HOLDER).build();
                    Beneficiary beneficiary = BenefitApi.get().createBeneficiary(registerDTO);
                    if(beneficiary != null) {
                        session.attribute("message","END Successfully Registered " + firstName + " " + secondName + " " +  lastName);
                    } else {
                        session.attribute("message","END Unsuccessfully Registered User");
                    }
                } catch (Exception ex) {
                    session.attribute("message","END Wrong date of birth input");
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            session.attribute("message", "END Invalid Registration Details");
        }
        return this;
    }
}
