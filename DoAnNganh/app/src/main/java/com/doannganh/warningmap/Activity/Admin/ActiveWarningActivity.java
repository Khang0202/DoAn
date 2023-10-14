package com.doannganh.warningmap.Activity.Admin;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.doannganh.warningmap.Object.StaticClass;
import com.doannganh.warningmap.Repository.WarningRepository;
import com.doannganh.warningmap.databinding.ActivityWarningActiveBinding;

public class ActiveWarningActivity extends AppCompatActivity {
    ActivityWarningActiveBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWarningActiveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBtn.setOnClickListener(v -> finish());
        binding.btnActive.setOnClickListener(v -> new WarningRepository()
                .activeWarning(
                        ActiveWarningActivity.this,
                        StaticClass.editWarning.getId()));
        Glide.with(ActiveWarningActivity.this).load(StaticClass.editWarning.getLinkImage())
                .transform(new GranularRoundedCorners(30,30,0,0))
                .into(binding.itemPic);
        binding.tvUploader.setText(StaticClass.editWarning.getUploader().getFirstName());
        binding.tvDistrict.setText(StaticClass.editWarning.getAddress().getDistrict().getDistrict());
        binding.tvProvine.setText(StaticClass.editWarning.getAddress().getProvince().getProvince());
        binding.tvTown.setText(StaticClass.editWarning.getAddress().getTown());
        binding.tvRoute.setText(StaticClass.editWarning.getAddress().getRoute());
        binding.tvStreet.setText(StaticClass.editWarning.getAddress().getStreetNumber());
        binding.tvLatitude.setText(String.valueOf(StaticClass.editWarning.getAddress().getLatitude()));
        binding.tvLongitude.setText(String.valueOf(StaticClass.editWarning.getAddress().getLongtitude()));


    }
}