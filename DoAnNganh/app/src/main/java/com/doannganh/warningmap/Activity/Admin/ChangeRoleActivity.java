package com.doannganh.warningmap.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.doannganh.warningmap.Object.StaticClass;
import com.doannganh.warningmap.R;
import com.doannganh.warningmap.Repository.UserRepository;
import com.doannganh.warningmap.databinding.ActivityChangeRoleBinding;

public class ChangeRoleActivity extends AppCompatActivity {
    ActivityChangeRoleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeRoleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBtn.setOnClickListener(v -> finish());

        binding.txtUserName.setText(StaticClass.userChangeRole.getUsername());
        binding.txtEmail.setText(StaticClass.userChangeRole.getEmail());
        binding.txtFirstName.setText(StaticClass.userChangeRole.getFirstName());
        if (StaticClass.userChangeRole.getRole().getId() == 1){
            binding.txtRoleName.setText("Admin");
            binding.btnChangeToAdmin.setVisibility(View.GONE);
            binding.btnChangeToUser.setOnClickListener(v -> new UserRepository().changeRole(ChangeRoleActivity.this,StaticClass.userChangeRole.getId(), 2));
        }
        if (StaticClass.userChangeRole.getRole().getId() == 2){
            binding.txtRoleName.setText("User");
            binding.btnChangeToUser.setVisibility(View.GONE);
            binding.btnChangeToAdmin.setOnClickListener(v -> new UserRepository().changeRole(ChangeRoleActivity.this,StaticClass.userChangeRole.getId(), 1));
        }
    }
}