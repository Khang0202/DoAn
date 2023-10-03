package com.doannganh.warningmap.Activity;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.doannganh.warningmap.Object.API;
import com.doannganh.warningmap.Object.StaticClass;
import com.doannganh.warningmap.R;
import com.doannganh.warningmap.databinding.ActivityLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private EditText edtPassword, edtUsername;
    private TextView txtForgetPassword;
    private Button loginButton, btnSingUp;
    private ImageView btnBack;
    private AwesomeValidation awesomeValidation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        anhXa();
        regex();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtUsername.getText().toString().equals("") || edtPassword.getText().toString().equals("")
                        || edtUsername.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    login();
                }
            }
        });
        btnBack.setOnClickListener(view -> {finish(); startActivity(new Intent(LoginActivity.this, MainActivity.class));});
        btnSingUp.setOnClickListener(view -> {finish(); startActivity(new Intent(LoginActivity.this, RegisterActivity.class));});
    }

    private void login() {
        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("username", edtUsername.getText().toString());
            jsonObject.put("password", edtPassword.getText().toString());

            Log.d("jsonObject", String.valueOf(jsonObject));

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,
                    API.login,
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Response", response.toString());
                            try {
                                if (response.getString("result").equals("Account does not exist")) {
                                    Toast.makeText(getApplicationContext(), "Tài khoản sai hoặc tài khoản đã bị khóa", Toast.LENGTH_SHORT).show();
                                } else if (response.getString("result").equals("Wrong password")) {
                                    Toast.makeText(getApplicationContext(), "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                                } else {

                                    Log.d("loginResponse", response.toString());
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class).
                                            setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                    Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.w("login_onErrorResponse", error.toString());
                        }
                    });
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(jsonObjectRequest);
        } catch (JSONException e) {
            Log.w("LoginErrorResponsed", String.valueOf(e));
            e.printStackTrace();
        }
    }

    private void anhXa() {
        txtForgetPassword = findViewById(R.id.txtForgetPassword);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);

        loginButton = findViewById(R.id.loginButton);
        btnBack = findViewById(R.id.backBtn);
        btnSingUp = findViewById(R.id.btnSingUp);
    }

    private void regex() {
        awesomeValidation = new AwesomeValidation(BASIC);
        awesomeValidation.addValidation(LoginActivity.this, R.id.edtUsername, "[a-zA-Z\\s]+", R.string.invalid_username);
        awesomeValidation.addValidation(LoginActivity.this, R.id.edtPassword, StaticClass.regexPassword, R.string.invalid_password);
    }
}