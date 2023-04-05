package com.example.foragersfriend;

import androidx.room.RoomDatabase;
import androidx.room.Database;

@Database(entities = {Mushroom.class}, version = 1)
abstract class AppDatabase extends RoomDatabase {

    abstract MushroomDao mushroomDao();

}
