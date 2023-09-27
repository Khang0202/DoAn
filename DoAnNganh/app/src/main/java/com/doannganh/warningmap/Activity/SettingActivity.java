package com.doannganh.warningmap.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.doannganh.warningmap.R;
import com.doannganh.warningmap.databinding.ActivityNoticeBinding;
import com.doannganh.warningmap.databinding.ActivitySettingBinding;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class SettingActivity extends AppCompatActivity {
    private ActivitySettingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.navBar.setItemSelected(R.id.nav_setting, true);setUpTabBar();
    }

    private void setUpTabBar() {
        binding.navBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                if (i == R.id.nav_map) {
                    //bắt đầu main activity
                    Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (i == R.id.nav_notice) {
                    //bắt đầu setting activity
                    Intent intent = new Intent(SettingActivity.this, NoticeActivity.class);
                    startActivity(intent);
                } else if (i == R.id.nav_setting) {
                    //bắt đầu notice activity
                    Intent intent = new Intent();
                    finish();
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}