package com.doannganh.warningmap.Fragment;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.doannganh.warningmap.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}