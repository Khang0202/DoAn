package com.doannganh.warningmap.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.doannganh.warningmap.Activity.Adapter.WarningActivedAdapter;
import com.doannganh.warningmap.Object.StaticClass;
import com.doannganh.warningmap.R;
import com.doannganh.warningmap.databinding.ActivityNoticeBinding;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class WarningNearbyActivity extends AppCompatActivity {
    private ActivityNoticeBinding binding;
    RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoticeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.navBar.setItemSelected(R.id.nav_notice, true);setUpTabBar();

        binding.rcvListWarning.setLayoutManager(new GridLayoutManager(WarningNearbyActivity.this, 1));
        adapter = new WarningActivedAdapter(StaticClass.activeWarningList);
        binding.rcvListWarning.setAdapter(adapter);

    }
    private void setUpTabBar() {

        binding.navBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                if (i == R.id.nav_map) {
                    //bắt đầu main activity
                    Intent intent = new Intent(WarningNearbyActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (i == R.id.nav_setting) {
                    //bắt đầu setting activity
                    if (StaticClass.userToken == null) {
                        Intent intent = new Intent(WarningNearbyActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        //bắt đầu setting activity
                        Intent intent = new Intent(WarningNearbyActivity.this, SettingActivity.class);
                        startActivity(intent);
                    }
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