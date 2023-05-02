package com.example.foragersfriend;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Mushroom")
public class Mushroom {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public final String name;
    @ColumnInfo(name = "description")
    public final String description;
    @ColumnInfo(name = "image")
    public final byte[] image;
    @ColumnInfo(name = "location")
    public final String location;
    @ColumnInfo(name = "date")
    public final String date;
    @ColumnInfo(name = "season")
    public final String season;
    @ColumnInfo(name = "toxicity")
    public final boolean isToxic;
    @ColumnInfo(name = "type")
    public final String type;

    public Mushroom(String name, String description, byte[] image, String location, String date, String season, boolean isToxic, String type) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.location = location;
        this.date = date;
        this.season = season;
        this.isToxic = isToxic;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getImage() {
        return image;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
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