package com.doannganh.warningmap.Object;

public class API {
    //HOST
    public static String localhost = "http://192.168.1.2:7297/api/";
    //USER
    public static String login = localhost + "user/login";
    public static String register = localhost + "user/register";
    public static String forgotPassword = localhost + "user/forgotPass";

    //Address
    public static String getProvince = localhost + "address/getProvince";
    public static String getDistrict = localhost + "address/getDistrict";

    //WARNING
    public static String getAllActiveWarning = localhost + "warning/getAllActiveWarning";
    public static String addWarning = localhost + "warning/addwaring";

    //ADMIN
    public static String getUserInfo = localhost + "user/getUserInfo";
    public static String activeWaring = localhost + "admin/activeWarning";
    public static String deActiveWarning = localhost + "admin/deActiveWarning";
    public static String getALlListUser = localhost + "admin/ListUser";


}
