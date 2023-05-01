package com.example.foragersfriend;

import static android.content.ContentValues.TAG;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MushroomDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mushroom_detail);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String mushroomName = extras.getString("mushroomName");
            String mushroomLastSeen = extras.getString("mushroomLastSeen");

            TextView textView = findViewById(R.id.mushroom_detail_name_actual_textview);
            textView.setText(mushroomName);

            TextView textView2 = findViewById(R.id.mushroom_detail_last_seen_actual_textview);
            textView2.setText(mushroomLastSeen);


        }
    }

}
