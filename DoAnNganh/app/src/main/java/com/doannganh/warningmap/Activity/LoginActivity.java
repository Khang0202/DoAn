package com.doannganh.warningmap.Activity;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
import com.doannganh.warningmap.Repository.UserRepository;
import com.doannganh.warningmap.databinding.ActivityLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private EditText edtPassword, edtUsername, edtInputMail;
    private TextView txtForgetPassword;
    private Button loginButton, btnSingUp, btnMailSend;
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
                    Toast.makeText(LoginActivity.this, "Please fill all the required information.", Toast.LENGTH_SHORT).show();
                } else {
//                    if (awesomeValidation.validate()) {
                        login();
//                    } else {
//                        Toast.makeText(LoginActivity.this, "Invalid", Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        });
        txtForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                View sendmailDialog = LayoutInflater.from(LoginActivity.this).inflate(R.layout.dialog_input_mail, null);
                builder.setView(sendmailDialog);

                edtInputMail = sendmailDialog.findViewById(R.id.edtInputMail);
                btnMailSend = sendmailDialog.findViewById(R.id.btnMailSend);

                AlertDialog alertDialog = builder.create();
                btnMailSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new UserRepository().forgotPassword(LoginActivity.this, edtInputMail.getText().toString());
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        btnBack.setOnClickListener(view -> {
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        });
        btnSingUp.setOnClickListener(view -> {
            finish();
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void login() {
        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("username", edtUsername.getText().toString());
            jsonObject.put("password", edtPassword.getText().toString());

            Log.d("NOTElogininput", String.valueOf(jsonObject));

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    API.login,
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Response", response.toString());
                            try {
                                if (response.has("result")) {
                                    if (response.getString("result").equals("Invalid username or password")) {
                                        Toast.makeText(getApplicationContext(), "Your username or password is incorrect.", Toast.LENGTH_SHORT).show();
                                    }
                                } else if (response.has("token")) {
                                    StaticClass.userToken = response.getString("token");
                                    new UserRepository().getUserInfo(LoginActivity.this);
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    Toast.makeText(getApplicationContext(), "Login successful.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.w("NOTElogin_onErrorResponse", error.toString());
                        }
                    });
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(jsonObjectRequest);
        } catch (JSONException e) {
            Log.w("NOTELoginJSONException", e.getMessage());
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