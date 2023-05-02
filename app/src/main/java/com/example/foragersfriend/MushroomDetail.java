package com.example.foragersfriend;

import static android.content.ContentValues.TAG;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class MushroomDetail extends AppCompatActivity {
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mushroom_detail);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "my-db").allowMainThreadQueries().build();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String mushroomName = extras.getString("mushroomName");

            AsyncTask.execute(() -> {
                Mushroom mushroom = db.mushroomDao().getByName(mushroomName);

                runOnUiThread(() -> {
                    if (mushroom != null) {
                        TextView nameTextView = findViewById(R.id.mushroom_detail_name_actual_textview);
                        nameTextView.setText(mushroom.getName());

                        TextView locationTextView = findViewById(R.id.mushroom_detail_last_seen_actual_textview);
                        locationTextView.setText(mushroom.getLocation());

                        TextView typeTextView = findViewById(R.id.mushroom_detail_type_actual_textview);
                        typeTextView.setText(mushroom.getType());

                        TextView toxicityActualTextView = findViewById(R.id.mushroom_detail_toxicity_actual_textview);
                        toxicityActualTextView.setText(mushroom.getToxicity());

                        TextView seasonTextView = findViewById(R.id.mushroom_detail_season_actual_textview);
                        seasonTextView.setText(mushroom.getSeason());

                        TextView descriptionTextView = findViewById(R.id.mushroom_detail_description_actual_textview);
                        descriptionTextView.setText(mushroom.getDescription());

                        Log.d("MushroomDetail", "Description: " + mushroom.getDescription());

                    }
                });
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}

