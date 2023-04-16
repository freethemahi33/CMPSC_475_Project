package com.example.foragersfriend;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Mushroom")
public class Mushroom {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public final int id;
    @ColumnInfo(name = "name")
    public final String name;
    @ColumnInfo(name = "description")
    public final String description;
    @ColumnInfo(name = "image")
    public final String image;
    @ColumnInfo(name = "location")
    public final String location;
    @ColumnInfo(name = "season")
    public final String season;
    @ColumnInfo(name = "toxicity")
    public final String toxicity;
    @ColumnInfo(name = "type")
    public final String type;

    public Mushroom(int id, String name, String description, String image, String location, String season, String toxicity, String type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.location = location;
        this.season = season;
        this.toxicity = toxicity;
        this.type = type;
    }
}