package com.example.foragersfriend;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.foragersfriend.NavFragments.HomeFragment;
import com.example.foragersfriend.NavFragments.MushroomAddFragment;
import com.example.foragersfriend.NavFragments.MushroomListFragment;
import com.example.foragersfriend.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Log.d(TAG, "onCreate: Switch");
            switch (item.getItemId()) {
                case R.id.homeFragment:
                    replaceFragment(new HomeFragment());
                    Log.d(TAG, "onCreate: Home");
                    break;
                case R.id.addMushroomFragment:
                    replaceFragment(new MushroomAddFragment());
                    Log.d(TAG, "onCreate: Add");
                    break;
                case R.id.mushroomListFragment:
                    replaceFragment(new MushroomListFragment());
                    Log.d(TAG, "onCreate: List");
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }
}