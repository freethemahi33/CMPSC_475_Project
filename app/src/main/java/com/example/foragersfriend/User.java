package com.example.foragersfriend;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tablename = "Users")
public class User {
    public User(int id, String userName, int startLocation, int endLocation) {
        this.id = id;
        this.userName = userName;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    public int id;

    @ColumnInfo(name = "userName")
    public String userName;

    @ColumnInfo(startLocation = "startLocation")
    public int startLocation;

    @ColumnInfo(endLocation = "endLocation")
    public int endLocation;
}
