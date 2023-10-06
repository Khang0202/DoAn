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
import com.doannganh.warningmap.Activity.MainActivity;
import com.doannganh.warningmap.Object.Address;
import com.doannganh.warningmap.Object.District;
import com.doannganh.warningmap.Object.Province;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WarningRepository {
    private Address address;

    public WarningRepository(Address placeInfo) {
        this.address = placeInfo;
    }

    public void getPlaceInfo(Context context, LatLng latLng){
        String latlng = latLng.latitude+","+ latLng.longitude;
        String url = Uri.parse("https://maps.googleapis.com/maps/api/geocode/json")
                .buildUpon()
                .appendQueryParameter("latlng", latlng)
                .appendQueryParameter("key", "AIzaSyD8Hy1UM4itV7K9hjz1M8CQLmchn7LMEP4")
                .toString();
        Log.d("NOTE", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    if (status.equals("OK")){
                        JSONArray results = response.getJSONArray("results");
                        for (int i=0;i<results.length();i++){
                            JSONArray address_components = results.getJSONObject(i).getJSONArray("address_components");
                            for (int j=0;j<address_components.length();j++){
                                JSONArray types = address_components.getJSONObject(j).getJSONArray("types");
                                String longName = address_components.getJSONObject(j).getString("long_name");
                                for (int k = 0; k < types.length(); k++) {
                                    String type = types.get(k).toString();
                                    if (type.equals("street_number")) {
                                        address.setStreetNumber(longName);
                                    }else if (type.equals("route")){
                                        address.setRoute(longName);
                                    }else if (type.equals("administrative_area_level_3")){
                                        address.setTown(longName);
                                    }else if (type.equals("administrative_area_level_2")){
                                        address.setDistrict(new District(longName));
                                    }else if (type.equals("administrative_area_level_1")){
                                        address.setProvince(new Province(longName));
                                    }
                                }
                            }
                        }
                        Log.d("NOTE",address.getStreetNumber()+address.getRoute()+address.getTown()+address.getDistrict().getDistrict()+address.getProvince().getProvince());
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("NOTE",error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }
}
