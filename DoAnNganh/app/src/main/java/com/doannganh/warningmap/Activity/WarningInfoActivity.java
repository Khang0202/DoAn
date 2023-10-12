package com.doannganh.warningmap.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.doannganh.warningmap.R;
import com.doannganh.warningmap.databinding.ActivityWarningInfoBinding;

public class WarningInfoActivity extends AppCompatActivity {
    ActivityWarningInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWarningInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}