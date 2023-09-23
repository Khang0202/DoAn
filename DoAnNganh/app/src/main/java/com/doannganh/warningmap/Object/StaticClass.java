package com.doannganh.warningmap.Object;

import android.location.Location;

public class StaticClass {
    public static String MD5(String password){
        return password;
    }
    public static Location currentLocation;
    public static String regexPassword = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$";
}
