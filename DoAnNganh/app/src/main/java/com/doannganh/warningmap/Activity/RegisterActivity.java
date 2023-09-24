package com.doannganh.warningmap.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.doannganh.warningmap.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtPassword, edtUsername, edtEmail, edtPhone, edtFirstName, edtLastName;
    private Button btnRegister;
    private ImageView btnBack;
    private AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnBack = findViewById(R.id.backBtn);
        btnBack.setOnClickListener(view -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
    }
}