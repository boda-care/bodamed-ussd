package com.bodamed.ussd.util;

import com.google.gson.Gson;
import okhttp3.*;
import com.bodamed.ussd.BuildConfig;

public class Constants {
    private static OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static String createBeneficiaryURI = BuildConfig.ACCOUNT_SERVICE + "/beneficiary/create";
    public static String getBeneficiaryAccountsURI = BuildConfig.ACCOUNT_SERVICE + "/beneficiary/accounts";
    public static String loginURI = BuildConfig.ACCOUNT_SERVICE + "/user/login";
    public static String getUserByPhoneNumberURI = BuildConfig.ACCOUNT_SERVICE + "/user/userByPhoneNumber";
    public static String getBenefitByNameURI = BuildConfig.ACCOUNT_SERVICE + "/benefit/name";
    public static String getAccountBalance  = BuildConfig.ACCOUNT_SERVICE + "/finance/balance";
    public static String getPackagePremiums  = BuildConfig.ACCOUNT_SERVICE + "/account/insurance_premiums";
    public static String getPackagePreActivationPremiums  = BuildConfig.ACCOUNT_SERVICE + "/account/insurance_preactivation_premiums";
    public static String getPremiumsPayable = BuildConfig.ACCOUNT_SERVICE + "/account/insurance_premiums";
    public static String initializeSTKPush  = BuildConfig.ACCOUNT_SERVICE + "/finance/initateStkPush";
    public static String acceptTandCs = BuildConfig.ACCOUNT_SERVICE + "/account/acceptTermsAndConditions";
    public static String payPremium = BuildConfig.ACCOUNT_SERVICE + "/account/payPremium";
    public static String getCommonCovers = BuildConfig.ACCOUNT_SERVICE + "/claim/commonCovers";
    public static String createClaim = BuildConfig.ACCOUNT_SERVICE + "/claim";
    public static String payForExpiredAccount = BuildConfig.ACCOUNT_SERVICE + "/account/payExpiredPremium";
    public static String getInsuranceCoverLimits = BuildConfig.ACCOUNT_SERVICE + "/account/packageLimits";
    public static String userController  = BuildConfig.ACCOUNT_SERVICE + "/user";

    public static <T, E> E createPostRequest(String URI, T arg, Class<E> target) {
        try {
            RequestBody body = RequestBody.create(JSON, new Gson().toJson(arg));
            Request request = new Request.Builder()
                    .url(URI)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            return new Gson().fromJson(response.body().string(), target);
        } catch (Exception exception){
            exception.printStackTrace();
            return null;
        }
    }

    public static <T, E> E createGetRequest(String URI , Class<E> target) {
        try {
            Request request = new Request.Builder()
                    .url(URI)
                    .get()
                    .build();
            Response response = client.newCall(request).execute();
            return new Gson().fromJson(response.body().string(), target);
        } catch (Exception exception){
            exception.printStackTrace();
            return null;
        }
    }

    public static String createGetRequest(String URI) {
        try {
            Request request = new Request.Builder()
                    .url(URI)
                    .get()
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception exception){
            exception.printStackTrace();
            return null;
        }
    }

    public static String sanitizePhoneNumber(String phoneNumber) {
        return phoneNumber.replaceAll("\\+", "");
    }
}
