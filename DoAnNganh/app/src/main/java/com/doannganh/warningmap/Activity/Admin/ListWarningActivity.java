package com.doannganh.warningmap.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.doannganh.warningmap.Activity.Adapter.UserChangeRoleAdapter;
import com.doannganh.warningmap.Activity.Adapter.WarningListAdapter;
import com.doannganh.warningmap.Object.API;
import com.doannganh.warningmap.Object.Address;
import com.doannganh.warningmap.Object.District;
import com.doannganh.warningmap.Object.Province;
import com.doannganh.warningmap.Object.StaticClass;
import com.doannganh.warningmap.Object.User;
import com.doannganh.warningmap.Object.Warning;
import com.doannganh.warningmap.R;
import com.doannganh.warningmap.Repository.WarningRepository;
import com.doannganh.warningmap.databinding.ActivityListWarningBinding;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListWarningActivity extends AppCompatActivity {

    ActivityListWarningBinding binding;
    List<Warning> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListWarningBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBtn.setOnClickListener(v -> finish());
        getAllWarning(ListWarningActivity.this);
        Log.d("NOTE", String.valueOf(items.size()));

    }
    public void getAllWarning(Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + StaticClass.userToken);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                API.getAllWarning,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("NOTE", response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = (JSONObject) response.get(i);
                                LatLng lng = new LatLng(Double.valueOf(object.getString("latitude")), Double.valueOf(object.getString("longtitute")));
                                Warning warning = new Warning();
                                warning.setId(object.getInt("id"));
                                warning.setLinkImage(object.getString("info"));
                                User user = new User();
                                user.setFirstName(object.getString("firstName"));
                                warning.setUploader(user);
                                Address address = new Address();
                                Province province = new Province();
                                province.setProvince(object.getString("province"));
                                address.setProvince(province);
                                District district = new District();
                                district.setDistrict(object.getString("district"));
                                address.setDistrict(district);
                                address.setRoute(object.getString("route"));
                                address.setTown(object.getString("town"));
                                address.setStreetNumber(object.getString("streetNumber"));
                                address.setLatitude(lng.latitude);
                                address.setLongtitude(lng.longitude);
                                warning.setAddress(address);
                                items.add(warning);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        binding.ListWarning.setLayoutManager(new GridLayoutManager(ListWarningActivity.this, 1));
                        binding.ListWarning.setAdapter(new WarningListAdapter(items));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return headers;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonArrayRequest);
    }
}