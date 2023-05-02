package com.example.foragersfriend;

import static android.content.ContentValues.TAG;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

public class MushroomDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mushroom_detail);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String mushroomName = extras.getString("mushroomName");
            String mushroomLastSeen = extras.getString("mushroomLastSeen");
            String mushroomDescription = extras.getString("mushroomDescription");
            String mushroomLocation = extras.getString("mushroomLocation");
            boolean mushroomToxicity = extras.getBoolean("mushroomToxicity");
            String mushroomSeason = extras.getString("mushroomSeason");
            String mushroomType = extras.getString("mushroomType");
            byte[] mushroomImage = extras.getByteArray("mushroomImage");

            ImageView imageView = findViewById(R.id.mushroom_detail_imageview);
            Bitmap bitmap = BitmapFactory.decodeByteArray(mushroomImage, 0, mushroomImage.length);
            imageView.setImageBitmap(bitmap);
            imageView.setScaleY(-1);
            imageView.setRotation(270);

            TextView name = findViewById(R.id.mushroom_detail_name_actual_textview);
            name.setText(mushroomName);

            TextView description = findViewById(R.id.mushroom_detail_description_actual_textview);
            description.setText(mushroomDescription);

            TextView location = findViewById(R.id.mushroom_detail_location_actual_textview);
            location.setText(mushroomLocation);

            TextView lastSeen = findViewById(R.id.mushroom_detail_last_seen_actual_textview);
            lastSeen.setText(mushroomLastSeen);

            TextView toxicity = findViewById(R.id.mushroom_detail_toxicity_actual_textview);
            toxicity.setText(mushroomToxicity ? "Toxic" : "Non-Toxic");

            TextView season = findViewById(R.id.mushroom_detail_season_actual_textview);
            season.setText(mushroomSeason);

            TextView type = findViewById(R.id.mushroom_detail_type_actual_textview);
            type.setText(mushroomType);

        }
    }

    public void deleteMushroom(View view) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int mushroomId = extras.getInt("mushroomId");
            MushroomDatabase db = Room.databaseBuilder(this, MushroomDatabase.class, "my-db").allowMainThreadQueries().build();
            MushroomDao mushroomDao = db.mushroomDao();
            mushroomDao.deleteById(mushroomId);
            db.close();
            finish();
        }
    }

    public void goBack(View view) {
        finish();
    }
}
