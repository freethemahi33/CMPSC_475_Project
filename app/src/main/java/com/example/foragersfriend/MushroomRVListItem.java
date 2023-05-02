package com.example.foragersfriend;

import android.content.Context;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class MushroomRVListItem {

    public final int id;
    private final String name;
    private final String description;

    private final byte[] image;
    private final String location;
    private final String date;
    private final String season;
    private final boolean isToxic;
    private final String type;

    public MushroomRVListItem(int id, String name, String description, byte[] image, String location, String date, String season, boolean isToxic, String type) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.location = location;
        this.date = date;
        this.season = season;
        this.isToxic = isToxic;
        this.type = type;
    }

    public static List<MushroomRVListItem> getDummyList(Context context) {
        MushroomDatabase db = Room.databaseBuilder(context, MushroomDatabase.class, "my-db").allowMainThreadQueries().build();
        Mushroom[] mushrooms = db.mushroomDao().getAll();

        List<MushroomRVListItem> mushroomRVListItems = new ArrayList<>();
        for (Mushroom mushroom : mushrooms) {

            MushroomRVListItem mushroomRVListItem = new MushroomRVListItem(
                    mushroom.getId(),
                    mushroom.getName(),
                    mushroom.getDescription(),
                    mushroom.getImage(),
                    mushroom.getLocation(),
                    mushroom.getDate(),
                    mushroom.getSeason(),
                    mushroom.getIsToxic(),
                    mushroom.getType()
            );

            mushroomRVListItems.add(mushroomRVListItem);
        }

        db.close();

        return mushroomRVListItems;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte[] getImageBytes() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getSeason() {
        return season;
    }

    public boolean getIsToxic() {
        return isToxic;
    }

    public String getType() {
        return type;
    }
}