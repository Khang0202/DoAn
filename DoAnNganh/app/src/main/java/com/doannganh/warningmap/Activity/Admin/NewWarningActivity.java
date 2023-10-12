package com.doannganh.warningmap.Activity.Admin;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.doannganh.warningmap.R;
import com.doannganh.warningmap.databinding.ActivityNewWarningBinding;

public class NewWarningActivity extends AppCompatActivity {
    ActivityNewWarningBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewWarningBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}