package com.bodamed.ussd.api;

import com.bodamed.ussd.domain.user.User;
import com.bodamed.ussd.util.Constants;
import com.bodamed.ussd.util.LoginDTO;

public class UserApi {
    private static UserApi INSTANCE = new UserApi();

    private UserApi() { }

    public User login(LoginDTO loginDTO) {
        return Constants.createPostRequest(Constants.loginURI, loginDTO, User.class);
    }

    public User findUserByPhoneNumber(String phoneNumber) {
        final String url = Constants.getUserByPhoneNumberURI.concat("?phoneNumber=").concat(phoneNumber);
        return Constants.createGetRequest(url, User.class);
    }

    public static UserApi get() {
        return INSTANCE;
    }
}