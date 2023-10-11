package com.doannganh.warningmap.Object;

import android.location.Location;

import java.util.List;

public class StaticClass {
    public static User currentUser;
    public static String userToken;
    public static List<Province> provinceList;
    public static List<District> districtList;
    public static String regexPassword = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$";
}
