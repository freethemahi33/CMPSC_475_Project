package com.example.foragersfriend;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MushroomDao {

    @Query("SELECT * FROM Mushroom")
    Mushroom[] getAll();

    @Query("SELECT * FROM Mushroom WHERE id = :id")
    Mushroom getById(int id);

    @Query("SELECT * FROM Mushroom WHERE name = :name")
    Mushroom getByName(String name);

    @Query("SELECT * FROM Mushroom WHERE location = :location")
    Mushroom getByLocation(String location);

    @Query("SELECT * FROM Mushroom WHERE season = :season")
    Mushroom getBySeason(String season);

    @Query("SELECT * FROM Mushroom WHERE toxicity = :toxicity")
    Mushroom getByToxicity(String toxicity);

    @Query("SELECT * FROM Mushroom WHERE type = :type")
    Mushroom getByType(String type);

    @Insert
    void insert(Mushroom ... mushrooms);

    @Delete
    void delete(Mushroom ... mushrooms);

    @Query("DELETE FROM Mushroom where id = :id")
    void deleteById(int id);

    @Delete
    void deleteAll();

    @Update
    void update(Mushroom ... mushrooms);
}
