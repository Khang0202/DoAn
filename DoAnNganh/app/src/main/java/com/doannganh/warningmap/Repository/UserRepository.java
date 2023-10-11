package com.doannganh.warningmap.Repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.doannganh.warningmap.Object.API;
import com.doannganh.warningmap.Object.Formater;
import com.doannganh.warningmap.Object.StaticClass;
import com.doannganh.warningmap.Object.User;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    public void getUserInfo(Context context){
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + StaticClass.userToken);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                API.getUserInfo,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("NOTE", response.toString());
                        try {
                            User user = new User();
                            user.setId(Integer.parseInt(response.getString("id")));
                            user.setUsername(response.getString("username").toString());
                            user.setFirstName(response.getString("firstname").toString());
                            user.setLastName(response.getString("lastname").toString());
                            user.setBirthday(new Formater().parseStringToDate(response.getString("birthday")));
                            user.setEmail(response.getString("email").toString());
                            user.setPhone(Integer.parseInt(response.getString("phone")));
                            StaticClass.currentUser = user;
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("NOTE", error.getMessage());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }
    public void forgotPassword(Context context, String email){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
            Log.d("NOTEforgotPassword",jsonObject.toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    API.forgotPassword,
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Toast.makeText(context, response.getString("result").toString(),Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("NOTEforgotPassword", error.getMessage());
                        }
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(jsonObjectRequest);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
}
