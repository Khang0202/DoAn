package com.doannganh.warningmap.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.doannganh.warningmap.Activity.Admin.AllUserInfoActivity;
import com.doannganh.warningmap.Activity.Admin.AllWarningActivity;
import com.doannganh.warningmap.Activity.User.ChangeInfoActivity;
import com.doannganh.warningmap.Activity.User.ChangePasswordActivity;
import com.doannganh.warningmap.Activity.User.UserInfoActivity;
import com.doannganh.warningmap.Object.StaticClass;
import com.doannganh.warningmap.R;
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
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticClass.currentUser = null;
                StaticClass.userToken = null;
                startActivity(new Intent(SettingActivity.this, MainActivity.class));
            }
        });
        binding.txtChangeInfo.setOnClickListener(v -> startActivity(new Intent(SettingActivity.this, ChangeInfoActivity.class)));
        binding.txtChangePassword.setOnClickListener(v -> startActivity(new Intent(SettingActivity.this, ChangePasswordActivity.class)));
        binding.tvUserName.setText(StaticClass.currentUser.getUsername());
        binding.txtEmail.setText(StaticClass.currentUser.getEmail());
        binding.txtUserInfo.setOnClickListener(v -> startActivity(new Intent(SettingActivity.this, UserInfoActivity.class)));
        if (StaticClass.currentUser.getRole().getId() == 1){
            binding.linearUserManage.setVisibility(View.VISIBLE);
            binding.linearWarningManage.setVisibility(View.VISIBLE);
            binding.txtUserManage.setOnClickListener(v -> startActivity(new Intent(SettingActivity.this, AllUserInfoActivity.class)));
            binding.txtWarningManage.setOnClickListener(v -> startActivity(new Intent(SettingActivity.this, AllWarningActivity.class)));
        }else if (StaticClass.currentUser.getRole().getId() == 3){
            binding.linearUserManage.setVisibility(View.GONE);
            binding.linearWarningManage.setVisibility(View.VISIBLE);
            binding.txtWarningManage.setOnClickListener(v -> startActivity(new Intent(SettingActivity.this, AllWarningActivity.class)));
            binding.txtUserManage.setOnClickListener(v -> Toast.makeText(SettingActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show());
        }else if (StaticClass.currentUser.getRole().getId() == 2){
            binding.linearUserManage.setVisibility(View.GONE);
            binding.linearWarningManage.setVisibility(View.GONE);
            binding.txtWarningManage.setOnClickListener(v -> Toast.makeText(SettingActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show());
            binding.txtUserManage.setOnClickListener(v -> Toast.makeText(SettingActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show());
        }

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
                    Intent intent = new Intent(SettingActivity.this, WarningNearbyActivity.class);
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