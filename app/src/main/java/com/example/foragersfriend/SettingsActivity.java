package com.example.foragersfriend;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get all fragments
        // Allows for switching between preference screens
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frag_settings, new SettingsFragment())
                .commit();
        setContentView(R.layout.activity_settings);
    }

    /** Main Settings Fragment **/
    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
            addPreferencesFromResource(R.xml.preferences);
        }
    }

    /** User Information Fragment **/
    public static class UserInfoFragment extends PreferenceFragmentCompat {
        private static final String TAG = "UserInfoFragment";

        @Override
        public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
            addPreferencesFromResource(R.xml.activity_setttings_user_info);

            // Get all EditTextPreferences
            EditTextPreference name = findPreference("name");
            EditTextPreference email = findPreference("email");
            EditTextPreference phone = findPreference("phone");
            EditTextPreference address = findPreference("address");

            // Set summary to current value
            if (name != null) {
                name.setSummaryProvider(EditTextPreference.SimpleSummaryProvider.getInstance());
            }
            if (email != null) {
                email.setSummaryProvider(EditTextPreference.SimpleSummaryProvider.getInstance());
            }
            if (phone != null) {
                phone.setSummaryProvider(EditTextPreference.SimpleSummaryProvider.getInstance());
            }
            if (address != null) {
                address.setSummaryProvider(EditTextPreference.SimpleSummaryProvider.getInstance());
            }
        }
    }

    // Add more fragments here
}