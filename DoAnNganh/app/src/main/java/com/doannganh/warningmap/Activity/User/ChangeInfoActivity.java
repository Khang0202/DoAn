package com.doannganh.warningmap.Activity.User;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.doannganh.warningmap.Activity.RegisterActivity;
import com.doannganh.warningmap.Object.Formater;
import com.doannganh.warningmap.Object.StaticClass;
import com.doannganh.warningmap.R;
import com.doannganh.warningmap.databinding.ActivityChangeInfoBinding;

import java.time.LocalDate;
import java.util.Calendar;

public class ChangeInfoActivity extends AppCompatActivity {
    ActivityChangeInfoBinding binding;
    LocalDate birth;
    private AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        regex();
        final int year = Calendar.getInstance().get(Calendar.YEAR);
        final int month = Calendar.getInstance().get(Calendar.MONTH);
        final int day = Calendar.getInstance().get(Calendar.DATE);
        binding.edtFirstName.setText(StaticClass.currentUser.getFirstName());
        binding.edtLastName.setText(StaticClass.currentUser.getLastName());
        binding.edtPhone.setText(String.valueOf(StaticClass.currentUser.getPhone()));
        binding.edtEmail.setText(StaticClass.currentUser.getEmail());
        binding.edtBirthday.setText(StaticClass.currentUser.getBirthday().toString());
        binding.backBtn.setOnClickListener(v -> finish());
        binding.edtBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(ChangeInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        binding.edtBirthday.setText(dayOfMonth + "/" + month + "/" + year);
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
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()){
                    finish();
                }
            }
        });
    }
    private void regex() {
        awesomeValidation = new AwesomeValidation(BASIC);
        awesomeValidation.addValidation(ChangeInfoActivity.this, binding.edtFirstName.getId(), "[a-zA-Z\\s]+", R.string.invalid_name);
        awesomeValidation.addValidation(ChangeInfoActivity.this, binding.edtLastName.getId(), "[a-zA-Z\\s]+", R.string.invalid_name);
        awesomeValidation.addValidation(ChangeInfoActivity.this, binding.edtEmail.getId(), "^\\\\S+@\\\\S+\\\\.\\\\S+$", R.string.invalid_email);
        awesomeValidation.addValidation(ChangeInfoActivity.this, binding.edtPhone.getId(), "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$", R.string.invalid_phone);
    }
}