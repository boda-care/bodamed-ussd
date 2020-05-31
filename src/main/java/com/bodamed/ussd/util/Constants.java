package com.bodamed.ussd.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;
import com.bodamed.ussd.BuildConfig;

import java.util.List;

public class Constants {
    private static OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static String createBeneficiaryURI = BuildConfig.ACCOUNT_SERVICE + "/beneficiary/create";
    public static String getBeneficiaryAccountsURI = BuildConfig.ACCOUNT_SERVICE + "/beneficiary/accounts";
    public static String loginURI = BuildConfig.ACCOUNT_SERVICE + "/user/login";
    public static String getUserByPhoneNumberURI = BuildConfig.ACCOUNT_SERVICE + "/user/userByPhoneNumber";
    public static String getBenefitByNameURI = BuildConfig.ACCOUNT_SERVICE + "/benefit/name";

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
