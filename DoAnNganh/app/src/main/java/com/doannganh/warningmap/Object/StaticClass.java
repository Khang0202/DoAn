package com.doannganh.warningmap.Object;

import android.location.Location;

public class StaticClass {
    public static User currentUser;
    public static String userToken;
    public static String regexPassword = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$";
}
