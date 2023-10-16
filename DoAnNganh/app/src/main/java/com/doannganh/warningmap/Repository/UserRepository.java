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
import com.doannganh.warningmap.Object.Role;
import com.doannganh.warningmap.Object.StaticClass;
import com.doannganh.warningmap.Object.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    public void getUserInfo(Context context) {
        Map<String, String> headers = new HashMap<>();
        Log.d("NOTE", StaticClass.userToken);
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
                            Role role = new Role();
                            role.setId(response.getInt("roleid"));
                            user.setRole(role);
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

    public void forgotPassword(Context context, String email) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
            Log.d("NOTEforgotPassword", jsonObject.toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    API.forgotPassword,
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Toast.makeText(context, response.getString("result").toString(), Toast.LENGTH_SHORT).show();
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

    public void changeRole(Context context, int userId, int roleId) {
        try {
            Map<String, String> headers = new HashMap<>();

            headers.put("Authorization", "Bearer " + StaticClass.userToken);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", userId);
            jsonObject.put("roleid", roleId);
            Log.d("NOTEchangeRole", jsonObject.toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    API.changeRole,
                    jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Toast.makeText(context, response.getString("result").toString(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("NOTE_changeRole_onErrorResponse", error.getMessage());
                }
            }
            ) {
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
    public void changeInfo(Context context, User user){
        try {
            Map<String, String> headers = new HashMap<>();
            Log.d("NOTE", StaticClass.userToken);
            headers.put("Authorization", "Bearer " + StaticClass.userToken);

            JSONObject object = new JSONObject();
            object.put("firstName", user.getFirstName());
            object.put("lastName", user.getLastName());
            object.put("email", user.getEmail());
            object.put("phone", user.getPhone());
            object.put("birth", new Formater().parseDateToString(user.getBirthday()));

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    API.changeInfo,
                    object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Toast.makeText(context, response.getString("result"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
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

    public void changePassword(Context context, String oldPassword, String newPassword) {
        try {
            Map<String, String> headers = new HashMap<>();
            Log.d("NOTE", StaticClass.userToken);
            headers.put("Authorization", "Bearer " + StaticClass.userToken);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("oldpassword", oldPassword);
            jsonObject.put("newpassword", newPassword);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    API.changePass,
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Toast.makeText(context, response.getString("result"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("NOTE", error.getMessage());
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
}
