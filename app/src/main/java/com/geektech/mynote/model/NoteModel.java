package com.geektech.mynote.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class NoteModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String date;

    public NoteModel(String title, String date) {
        this.title = title;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
