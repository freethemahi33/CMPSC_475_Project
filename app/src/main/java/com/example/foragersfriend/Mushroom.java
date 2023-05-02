package com.example.foragersfriend;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

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

    public Mushroom(String name, String location, String description, String date, String season, boolean isToxic, String type) {
        this(0, name, description, String.valueOf(LocalDate.now()), location, season, isToxic ? "Toxic" : "Non-Toxic", type);
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

    public String getImage() {
        return image;
    }

    public String getLocation() {
        return location;
    }

    public String getSeason() {
        return season;
    }

    public String getToxicity() {
        return toxicity;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return LocalDate.now().toString();
    }

    @Override
    public String toString() {
        return "Mushroom{name='" + name + "', location='" + location + "', season='" + season + "', isToxic=" + toxicity + ", type='" + type + "', description='" + description + "'}";
    }




}
