package com.doannganh.warningmap.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Toast;

import com.doannganh.warningmap.Fragment.MapsFragment;
import com.doannganh.warningmap.R;
import com.doannganh.warningmap.Fragment.SettingsFragment;
import com.doannganh.warningmap.databinding.ActivityMainBinding;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {


    FragmentManager fragmentManager;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fragmentManager = getSupportFragmentManager();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setUpTabBar();
    }

    private void setUpTabBar() {

        binding.navBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                if (i == R.id.nav_map){
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, MapsFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack("map")
                            .commit();
                } else if (i == R.id.nav_setting){
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, SettingsFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack("setting")
                            .commit();
                }else {
                    Toast.makeText(getApplicationContext(), "Không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}