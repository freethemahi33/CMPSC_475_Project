package com.example.foragersfriend.BottomNavFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foragersfriend.R;
import androidx.fragment.app.Fragment;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}