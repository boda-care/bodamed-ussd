package com.bodamed.ussd.api;

import com.bodamed.ussd.domain.beneficiary.Beneficiary;
import com.bodamed.ussd.domain.beneficiary.Benefit;
import com.bodamed.ussd.domain.beneficiary.BenefitAccount;
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

    public static BenefitApi get() {
        return INSTANCE;
    }
}
