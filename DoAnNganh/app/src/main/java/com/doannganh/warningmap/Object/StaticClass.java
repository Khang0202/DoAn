package com.doannganh.warningmap.Object;

import android.location.Location;
import android.net.Uri;

import java.util.List;

public class StaticClass {
    public static User currentUser;
    public static User userChangeRole;
    public static Location currentLocation;
    public static Warning infoWarning;
    public static Warning editWarning;
    public static List<Warning> activeWarningList;
    public static List<Warning> allWarningList;
    public static String userToken;
    public static Uri imageUri;
    public static String tempUrl;
    public static boolean isCaptureOrNot = false;
    public static String testuserToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiYWRtaW4iLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiIxIiwiaXNzIjoiaHR0cDovLzE5Mi4xNjguMS4yOjcyOTciLCJhdWQiOiJEb0FuTmdhbmgtMjA1MTAxMDAwNC0yMDUxMDEwMTM0In0.Y3PSo0cj8ATGV_dsHPBC7bg2o_tIU9ydiHgbf2E83gQ";
    public static List<Province> provinceList;
    public static List<District> districtList;
    public static String regexPassword = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$";
}
