package com.doannganh.warningmap.Activity.Admin;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.doannganh.warningmap.Activity.Adapter.UserChangeRoleAdapter;
import com.doannganh.warningmap.Object.API;
import com.doannganh.warningmap.Object.Role;
import com.doannganh.warningmap.Object.StaticClass;
import com.doannganh.warningmap.Object.User;
import com.doannganh.warningmap.databinding.ActivityAllUserInfoBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllUserInfoActivity extends AppCompatActivity {
    ActivityAllUserInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllUserInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backBtn.setOnClickListener(v -> finish());
        initAllUser();
    }

    private void initAllUser() {
        ArrayList<User> items = new ArrayList<>();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + StaticClass.userToken);
        StringRequest sReq = new StringRequest(Request.Method.GET,
                API.listUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                Role role = new Role();
                                role.setId(object.getInt("roleId"));
                                User user = new User();
                                user.setUsername(object.getString("username"));
                                user.setId(object.getInt("id"));
                                user.setFirstName(object.getString("firstName"));
                                user.setEmail(object.getString("email"));
                                user.setRole(role);
                                items.add(user);
                            }
                            binding.ListUser.setLayoutManager(new GridLayoutManager(AllUserInfoActivity.this, 1));
                            binding.ListUser.setAdapter(new UserChangeRoleAdapter(items));
                        } catch (
                                JSONException e) {
                            Log.w("Load manufacturer", "Error: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return headers;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(sReq);
    }
}