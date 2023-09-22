package com.doannganh.warningmap.Data;

import android.util.Log;

import com.doannganh.warningmap.Object.LoggedInUser;
import com.doannganh.warningmap.Repository.LoginRepository;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private LoginRepository loginRepository;

    public LoggedInUser login(String username, String password) {

        try {

            // TODO: handle loggedInUser authentication
            LoggedInUser user =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            return user;
        } catch (Exception e) {
            Log.w("LoginError", e.getMessage().toString());
            return null;
        }
    }

    public void logout() {
        if (loginRepository.isLoggedIn())
            loginRepository.logout();
    }
}