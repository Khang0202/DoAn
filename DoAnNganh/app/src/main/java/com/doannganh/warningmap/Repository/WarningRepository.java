package com.doannganh.warningmap.Repository;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.doannganh.warningmap.Activity.MainActivity;
import com.doannganh.warningmap.Object.API;
import com.doannganh.warningmap.Object.Address;
import com.doannganh.warningmap.Object.District;
import com.doannganh.warningmap.Object.Province;
import com.doannganh.warningmap.Object.StaticClass;
import com.doannganh.warningmap.R;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WarningRepository {

    public void addWarning(Context context, Address address, LatLng latLng){
        Map<String, String> headers = new HashMap<>();
//        headers.put("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjgiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoic3RyaW5nYSIsImh0dHA6Ly9zY2hlbWFzLm1pY3Jvc29mdC5jb20vd3MvMjAwOC8wNi9pZGVudGl0eS9jbGFpbXMvcm9sZSI6IjIiLCJpc3MiOiJodHRwOi8vMTkyLjE2OC4xLjI6NzI5NyIsImF1ZCI6IkRvQW5OZ2FuaC0yMDUxMDEwMDA0LTIwNTEwMTAxMzQifQ.JAYuLAr-3YEy9C70yth4UHxElyG1PFXlpkOqjXL_KHY");
        headers.put("Authorization", "Bearer " + StaticClass.userToken);
        int idprovince = 0;
        for (Province province :StaticClass.provinceList) {
            if (address.getProvince().getProvince().equalsIgnoreCase(province.getProvince()))
                idprovince = province.getId();
        }
        int iddistrict = 0;
        for (District district :StaticClass.districtList) {
            if (address.getDistrict().getDistrict().equalsIgnoreCase(district.getDistrict()))
                iddistrict = district.getId();
        }
        try {
            JSONObject add = new JSONObject();
            JSONObject warning = new JSONObject();
            warning.put("infowarning", StaticClass.tempUrl);
            add.put("warning", warning);
            JSONObject coordinates = new JSONObject();
            coordinates.put("latitude", latLng.latitude);
            coordinates.put("longitude", latLng.longitude);
            add.put("coordinates", coordinates);
            JSONObject addressinfo = new JSONObject();
            addressinfo.put("idprovince", idprovince);
            addressinfo.put("iddistrinct", iddistrict);
            addressinfo.put("town", address.getTown());
            addressinfo.put("route", address.getRoute());
            addressinfo.put("streetNumber", address.getStreetNumber());
            add.put("address", addressinfo);
            Log.d("NOTEaddWarning", add.toString());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    API.addWarning,
                    add,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("NOTE", response.toString());
                            try {
                                StaticClass.tempUrl = null;
                                Toast.makeText(context, response.getString("result").toString(), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("NOTEaddWarningonErrorResponse", error.getMessage());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    return headers;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(jsonObjectRequest);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public String uploadToCloudinary(Context context){
        Map config = new HashMap();
        config.put("cloud_name", "dpx0fdsxd");
        config.put("api_key","115376384385299");
        config.put("api_secret","gr_ZA27GjkpPV3SRFLCwA9MQruA");
        MediaManager.init(context ,config);
        final String[] url = new String[1];

        MediaManager.get().upload(StaticClass.imageUri).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                Log.d("NOTE", "onStart: "+"started");
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
                Log.d("NOTE", "onStart: "+"uploading");
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                Log.d("NOTE", "onStart: "+"usuccess");
                url[0] = (String) resultData.get("url");
                StaticClass.imageUri = null;
                Log.d("NOTE", url[0]);
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                Log.d("NOTE", "onStart: "+error);
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
                Log.d("NOTE", "onStart: "+error);
            }
        }).dispatch();
        return url[0];
    }
}
