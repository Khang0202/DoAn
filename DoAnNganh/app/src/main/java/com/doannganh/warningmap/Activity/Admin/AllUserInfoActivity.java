package com.doannganh.warningmap.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.doannganh.warningmap.Activity.Adapter.UserChangeRoleAdapter;
import com.doannganh.warningmap.Object.API;
import com.doannganh.warningmap.Object.User;
import com.doannganh.warningmap.databinding.ActivityAllUserInfoBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
        StringRequest sReq = new StringRequest(Request.Method.GET,
                API.listUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject manufacturerObject = array.getJSONObject(i);
                                User user = new User(
                                        manufacturerObject.getInt("id"),
                                        manufacturerObject.getString("firstName"),
                                        manufacturerObject.getString("username"),
                                        manufacturerObject.getString("email"),
                                        manufacturerObject.getInt("roleId")
                                );
                                items.add(user);
                            }
                            binding.DanhSachNguoiDung.setLayoutManager(new GridLayoutManager(AllUserInfoActivity.this, 1));
                            binding.DanhSachNguoiDung.setAdapter(new UserChangeRoleAdapter(items));
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
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(sReq);
    }
}