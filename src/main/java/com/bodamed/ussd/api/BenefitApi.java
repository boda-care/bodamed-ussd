package com.bodamed.ussd.api;

import com.bodamed.ussd.domain.beneficiary.Beneficiary;
import com.bodamed.ussd.domain.beneficiary.Benefit;
import com.bodamed.ussd.domain.beneficiary.BenefitAccount;
import com.bodamed.ussd.domain.beneficiary.InsurancePremium;
import com.bodamed.ussd.domain.finance.Finance;
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

    public BenefitAccount acceptTermsAndConditions (BenefitAccount benefitAccount) {
        final String url = Constants.acceptTandCs.concat("?accountId=").concat(Long.toString(benefitAccount.getId()));
        return Constants.createPostRequest(url,benefitAccount, BenefitAccount.class);
    }

    public Finance payPremium(long accountId, InsurancePremium premium) {
        final String url = Constants.payPremium.concat("?accountId=").concat(Long.toString(accountId));
        return Constants.createPostRequest(url, premium, Finance.class);
    }

    public static BenefitApi get() {
        return INSTANCE;
    }
}
