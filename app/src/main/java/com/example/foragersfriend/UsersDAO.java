package com.example.foragersfriend;

import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UsersDAO {

    @Query("SELECT * FROM Users WHERE rowid = :userId")
    user getById(int jokeId);

    @Insert
    void insert(user... Users);

    @Update
    void update(user... Users);

    @Delete
    void delete(user... Users);

    @Query("DELETE FROM Users WHERE rowid = :userId")
    void delete(int userId);
}
