package com.bodamed.ussd.api;

import com.bodamed.ussd.domain.finance.Balance;
import com.bodamed.ussd.util.Constants;
import com.bodamed.ussd.util.LipaMpesaDTO;

public class FinanceApi {
    private static FinanceApi INSTANCE = new FinanceApi();

    private FinanceApi() {}

    public Balance getBalance(long accountId) {
        final String url = Constants.getAccountBalance.concat("?id=").concat(Long.toString(accountId));
        return Constants.createGetRequest(url, Balance.class);
    }

    public LipaMpesaDTO save(LipaMpesaDTO lipaMpesaDTO) {
        return  Constants.createPostRequest(Constants.save, lipaMpesaDTO, LipaMpesaDTO.class);
    }

    public static FinanceApi get() {return INSTANCE;}
}
