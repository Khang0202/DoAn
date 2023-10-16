package com.doannganh.warningmap.Activity.User;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.doannganh.warningmap.Activity.LoginActivity;
import com.doannganh.warningmap.Object.StaticClass;
import com.doannganh.warningmap.R;
import com.doannganh.warningmap.Repository.UserRepository;
import com.doannganh.warningmap.databinding.ActivityChangePasswordBinding;

public class ChangePasswordActivity extends AppCompatActivity {
    ActivityChangePasswordBinding binding;
    private AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        regex();
        binding.backBtn.setOnClickListener(v -> finish());
        binding.btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()){
                    if (binding.edtConfirmPassword.getText().equals(binding.edtNewPassword.getText())){
                        new UserRepository().changePassword(ChangePasswordActivity.this,
                                binding.edtOldPassword.getText().toString(),
                                binding.edtConfirmPassword.getText().toString());
                    }
                }
            }
        });
    }
    private void regex() {
        awesomeValidation = new AwesomeValidation(BASIC);
        awesomeValidation.addValidation(ChangePasswordActivity.this, binding.edtOldPassword.getId(), StaticClass.regexPassword, R.string.invalid_password);
        awesomeValidation.addValidation(ChangePasswordActivity.this, binding.edtConfirmPassword.getId(), StaticClass.regexPassword, R.string.invalid_password);
        awesomeValidation.addValidation(ChangePasswordActivity.this, binding.edtNewPassword.getId(), StaticClass.regexPassword, R.string.invalid_password);
    }
}