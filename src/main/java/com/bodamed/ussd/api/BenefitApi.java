package com.bodamed.ussd.api;

import com.bodamed.ussd.domain.beneficiary.*;
import com.bodamed.ussd.domain.finance.Finance;
import com.bodamed.ussd.util.AccountPremiumDTO;
import com.bodamed.ussd.util.Constants;
import com.bodamed.ussd.util.RegisterDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class BenefitApi {
    private static BenefitApi INSTANCE = new BenefitApi();

    private BenefitApi(){
    }

    public Beneficiary createBeneficiary(RegisterDTO registerDTO){
        return Constants.createPostRequest(Constants.createBeneficiaryURI, registerDTO, Beneficiary.class);
    }

    public Benefit getBenefitByName(String name) {
        final String url = Constants.getBenefitByNameURI.concat("?name=").concat(name);
        return Constants.createGetRequest(url, Benefit.class);
    }

    public List<BenefitAccount> getBeneficiaryAccounts (long userId) {
        final String url = Constants.getBeneficiaryAccountsURI.concat("?userId=").concat(Long.toString(userId));
        String response = Constants.createGetRequest(url);
        return new Gson().fromJson(response, new TypeToken<List<BenefitAccount>>(){}.getType());
    }

    public List<InsurancePremium> getInsurancePremiums (long packageId) {
        final String url = Constants.getPackagePremiums.concat("?packageId=").concat(Long.toString(packageId));
        String response = Constants.createGetRequest(url);
        return new Gson().fromJson(response, new TypeToken<List<InsurancePremium>>(){}.getType());
    }

    public List<InsurancePremium> getInsurancePreActivationPremiums (long packageId) {
        final String url = Constants.getPackagePreActivationPremiums.concat("?packageId=").concat(Long.toString(packageId));
        String response = Constants.createGetRequest(url);
        return new Gson().fromJson(response, new TypeToken<List<InsurancePremium>>(){}.getType());
    }

    public BenefitAccount acceptTermsAndConditions (long userId, BenefitAccount account) {
        final String url = Constants.acceptTandCs.concat("?userId=").concat(Long.toString(userId));
        return Constants.createPostRequest(url,account, BenefitAccount.class);
    }

    public Finance payPremium(long accountId, InsurancePremium premium) {
        final String url = Constants.payPremium.concat("?accountId=").concat(Long.toString(accountId));
        return Constants.createPostRequest(url, premium, Finance.class);
    }

    public Finance payForExpiredAccount(AccountPremiumDTO accountPremiumDTO) {
        return Constants.createPostRequest(Constants.payForExpiredAccount, accountPremiumDTO, Finance.class);
    }

    public List<InsuranceCoverLimit> getPackageLimits(long beneficiaryId, long packageId) {
        try {
            final String url = Constants.getInsuranceCoverLimits.concat("?beneficiaryId=")
                    .concat(Long.toString(beneficiaryId)).concat("&packageId=").concat(Long.toString(packageId));
            String response = Constants.createGetRequest(url);
            System.out.println(response);
            return new Gson().fromJson(response, new TypeToken<List<InsuranceCoverLimit>>(){}.getType());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public static BenefitApi get() {
        return INSTANCE;
    }
}
