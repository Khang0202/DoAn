package com.doannganh.warningmap.Object;

public class API {
    //HOST
    public static String localhost = "http://192.168.1.2:7297/api/";
    //USER
    public static String login = localhost + "user/login";
    public static String getUserInfo = localhost + "user/getUserInfo";
    public static String register = localhost + "user/register";
    public static String changePass = localhost + "user/changePass";
    public static String forgotPassword = localhost + "user/forgotPass";
    public static String addWarning = localhost + "warning/addwaring";
    public static String getProvince = localhost + "address/getProvince";
    public static String getDistrict = localhost + "address/getDistrict";
    public static String getActiveWaring = localhost+"warning/getAllActiveWarning";
    public static String changeInfo = localhost+"user/changeInfo";

    //ADMIN
    public static String changeRole = localhost + "admin/ChangeRole";
    public static String listUser = localhost + "admin/ListUser";
    public static String activeWaring = localhost + "admin/activeWarning";
    public static String getAllWarning = localhost + "admin/getAllWarning";



}
