package com.doannganh.warningmap.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.doannganh.warningmap.R;
import com.doannganh.warningmap.databinding.ActivityMainBinding;
import com.doannganh.warningmap.databinding.ActivityNoticeBinding;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class NoticeActivity extends AppCompatActivity {
    private ActivityNoticeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoticeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.navBar.setItemSelected(R.id.nav_notice, true);setUpTabBar();

    }
    private void setUpTabBar() {

        binding.navBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                if (i == R.id.nav_map) {
                    //bắt đầu main activity
                    Intent intent = new Intent(NoticeActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (i == R.id.nav_setting) {
                    //bắt đầu setting activity
                    Intent intent = new Intent(NoticeActivity.this, SettingActivity.class);
                    startActivity(intent);
                } else if (i == R.id.nav_notice) {
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