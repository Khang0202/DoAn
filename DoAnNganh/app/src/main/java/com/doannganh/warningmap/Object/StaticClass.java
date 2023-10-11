package com.doannganh.warningmap.Object;

import android.location.Location;

import java.util.List;

public class StaticClass {
    public static User currentUser;
    public static String userToken;
    public static String testuserToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjgiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoic3RyaW5nYSIsImh0dHA6Ly9zY2hlbWFzLm1pY3Jvc29mdC5jb20vd3MvMjAwOC8wNi9pZGVudGl0eS9jbGFpbXMvcm9sZSI6IjIiLCJpc3MiOiJodHRwOi8vMTkyLjE2OC4xLjI6NzI5NyIsImF1ZCI6IkRvQW5OZ2FuaC0yMDUxMDEwMDA0LTIwNTEwMTAxMzQifQ.JAYuLAr-3YEy9C70yth4UHxElyG1PFXlpkOqjXL_KHY";
    public static List<Province> provinceList;
    public static List<District> districtList;
    public static String regexPassword = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$";
}
