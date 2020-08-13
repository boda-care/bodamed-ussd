package com.bodamed.ussd.api;

import com.bodamed.ussd.domain.beneficiary.InsuranceCover;
import com.bodamed.ussd.domain.claim.Claim;
import com.bodamed.ussd.domain.claim.ClaimProcessorContract;
import com.bodamed.ussd.util.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ClaimApi {
    private static ClaimApi INSTANCE = new ClaimApi();

    public static ClaimApi get() {
        return INSTANCE;
    }

    public List<ClaimProcessorContract> getCommonCovers(String claimProcessorCode, long benefitAccountId) {
        final String url = Constants.getCommonCovers.concat("?claimProcessorCode=")
                .concat(claimProcessorCode)
                .concat("&benefitAccountId=")
                .concat(Long.toString(benefitAccountId));
        String response = Constants.createGetRequest(url);
        return new Gson().fromJson(response, new TypeToken<List<ClaimProcessorContract>>(){}.getType());
    }

    public Claim createClaim(Claim claim)  {
        return Constants.createPostRequest(Constants.createClaim, claim, Claim.class);
    }
}
