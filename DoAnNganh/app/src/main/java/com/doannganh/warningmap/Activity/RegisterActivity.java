package com.doannganh.warningmap.Activity;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.doannganh.warningmap.Object.Formater;
import com.doannganh.warningmap.Object.StaticClass;
import com.doannganh.warningmap.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    LocalDate birth;
    private EditText edtPassword, edtUsername, edtEmail, edtPhone, edtBirthday, edtFirstName, edtLastName, edtConfirmPassword;
    private Button btnRegister;
    private ImageView btnBack;
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        btnBack.setOnClickListener(view -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
        final int year = Calendar.getInstance().get(Calendar.YEAR);
        final int month = Calendar.getInstance().get(Calendar.MONTH);
        final int day = Calendar.getInstance().get(Calendar.DATE);

        regex();
        edtBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        edtBirthday.setText(dayOfMonth + "/" + month + "/" + year);
                        Log.d("NOTEday", String.valueOf(dayOfMonth));
                        Log.d("NOTEmonth", String.valueOf(month));
                        Log.d("NOTEyear", String.valueOf(year));

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            birth = LocalDate.of(year, month, dayOfMonth);
                        }
                    }
                }, year, month, day);
                dialog.show();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()) {
                    if (edtFirstName.getText().toString() == null && edtFirstName.getText().toString().isEmpty() ||
                            edtLastName.getText().toString() == null && edtLastName.getText().toString().isEmpty() ||
                            edtEmail.getText().toString() == null && edtEmail.getText().toString().isEmpty() ||
                            edtPhone.getText().toString() == null && edtPhone.getText().toString().isEmpty() ||
                            edtBirthday.getText().toString() == null && edtBirthday.getText().toString().isEmpty() ||
                            edtUsername.getText().toString() == null && edtUsername.getText().toString().isEmpty() ||
                            edtPassword.getText().toString() == null && edtPassword.getText().toString().isEmpty() ||
                            edtConfirmPassword.getText().toString() != null && edtConfirmPassword.getText().toString().isEmpty()) {
                        Toast.makeText(RegisterActivity.this, "Please fill all the required information.", Toast.LENGTH_SHORT).show();
                    } else if (!edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString())) {
                        Toast.makeText(RegisterActivity.this, "The confirmed password does not match the password.", Toast.LENGTH_SHORT).show();
                    } else {
                        register();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void register() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("firstname", edtFirstName.getText().toString());
            jsonObject.put("lastname", edtLastName.getText().toString());
            jsonObject.put("email", edtEmail.getText().toString());
            jsonObject.put("phone", edtPhone.getText().toString());
            jsonObject.put("birth", new Formater().parseDateToString(birth));
            jsonObject.put("username", edtUsername.getText().toString());
            jsonObject.put("password", edtPassword.getText().toString());

            Log.d("NOTEregister", jsonObject.toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    API.register,
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getString("result").equals("Account created")) {
                                    Toast.makeText(RegisterActivity.this, "Register successful.", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                } else
                                    Toast.makeText(RegisterActivity.this, response.getString("result"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("NOTEregister_onErrorResponse", error.getMessage());
                }
            });
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(jsonObjectRequest);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


    }

    private void initView() {
        btnBack = findViewById(R.id.backBtn);
        btnRegister = findViewById(R.id.btnRegister);

        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtBirthday = findViewById(R.id.edtBirthday);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
    }

    private void regex() {
        awesomeValidation = new AwesomeValidation(BASIC);
        awesomeValidation.addValidation(RegisterActivity.this, R.id.edtFirstName, "[a-zA-Z\\s]+", R.string.invalid_name);
        awesomeValidation.addValidation(RegisterActivity.this, R.id.edtLastName, "[a-zA-Z\\s]+", R.string.invalid_name);
        awesomeValidation.addValidation(RegisterActivity.this, R.id.edtEmail, "^\\\\S+@\\\\S+\\\\.\\\\S+$", R.string.invalid_email);
        awesomeValidation.addValidation(RegisterActivity.this, R.id.edtPhone, "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$", R.string.invalid_phone);
        awesomeValidation.addValidation(RegisterActivity.this, R.id.edtUsername, "[a-zA-Z\\s]+", R.string.invalid_username);
        awesomeValidation.addValidation(RegisterActivity.this, R.id.edtPassword, StaticClass.regexPassword, R.string.invalid_password);
        awesomeValidation.addValidation(RegisterActivity.this, R.id.edtConfirmPassword, StaticClass.regexPassword, R.string.invalid_password);
    }
}