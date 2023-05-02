package com.example.foragersfriend;

import android.content.Context;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MushroomRVListItem {

    private String name;
    private String lastSeen;

    private static int image;

    public MushroomRVListItem(String name, String lastSeen, int image) {
        this.name = name;
        this.lastSeen = lastSeen;
        this.image = image;
    }

    public static List<MushroomRVListItem> getDummyList(Context context) {
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "my-db").allowMainThreadQueries().build();
        List<Mushroom> mushrooms = new ArrayList<>(Arrays.asList(db.mushroomDao().getAll()));


        List<MushroomRVListItem> mushroomRVListItems = new ArrayList<>();
        for (Mushroom mushroom : mushrooms) {
            // Check if the mushroom already exists in the list
            boolean alreadyAdded = false;
            for (MushroomRVListItem existingMushroom : mushroomRVListItems) {
                if (existingMushroom.getName().equals(mushroom.getName())) {
                    alreadyAdded = true;
                    break;
                }
            }
            if (!alreadyAdded) {
                MushroomRVListItem mushroomRVListItem = new MushroomRVListItem(
                        mushroom.getName(),
                        mushroom.getDate(),  // assuming 'lastSeen' is the date
                        image
                );
                mushroomRVListItems.add(mushroomRVListItem);
            }
        }

        db.close();

        return mushroomRVListItems;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
