package com.doannganh.warningmap.Repository;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.doannganh.warningmap.Object.API;
import com.doannganh.warningmap.Object.District;
import com.doannganh.warningmap.Object.Province;
import com.doannganh.warningmap.Object.StaticClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddressRepository {
    public void getAllProvince(Context context) {
        Log.d("NOTE", "callgetAllProvince");
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                API.getProvince, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                StaticClass.provinceList = new ArrayList<>();
                for (int i = 0; i < response.length(); i++){
                    Province province = new Province();
                    try {
                        JSONObject jsonObject  = (JSONObject) response.get(i);
                        province.setId((Integer) jsonObject.get("id"));
                        province.setProvince(jsonObject.getString("name"));

                        StaticClass.provinceList.add(province);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                Log.d("NOTEprovinceListsize", String.valueOf(StaticClass.provinceList.size()));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("NOTE", error.getMessage());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }
    public void getAllDistrict(Context context) {
        Log.d("NOTE", "callgetAllDistrict");
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                API.getDistrict, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                StaticClass.districtList = new ArrayList<>();
                for (int i = 0; i < response.length(); i++){
                    District district = new District();
                    try {
                        JSONObject jsonObject  = (JSONObject) response.get(i);
                        district.setId((Integer) jsonObject.get("id"));
                        district.setDistrict(jsonObject.getString("name"));

                        StaticClass.districtList.add(district);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                Log.d("NOTEdistrictListSize", String.valueOf(StaticClass.districtList.size()));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("NOTE", error.getMessage());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }
}
