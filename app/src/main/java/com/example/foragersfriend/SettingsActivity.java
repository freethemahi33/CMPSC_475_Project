package com.example.foragersfriend;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;

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
        @Override
        public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
            addPreferencesFromResource(R.xml.activity_setttings_user_info);
        }
    }

    // Add more fragments here
}