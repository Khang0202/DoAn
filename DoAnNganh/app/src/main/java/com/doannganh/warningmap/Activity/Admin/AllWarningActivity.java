package com.doannganh.warningmap.Activity.Admin;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.doannganh.warningmap.databinding.ActivityAllWarningBinding;

public class AllWarningActivity extends AppCompatActivity {
    ActivityAllWarningBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllWarningBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}