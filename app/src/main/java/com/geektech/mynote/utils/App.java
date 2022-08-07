package com.geektech.mynote.utils;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.geektech.mynote.room.NoteDatabase;

public class App extends Application {
    private static Context context;
    public static NoteDatabase database = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initDatabase();
        PrefHelper.init(this);
    }

    private static NoteDatabase initDatabase() {
        if (database == null) {
            database = Room.databaseBuilder(context,
                    NoteDatabase.class, "database-note")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public static NoteDatabase getDatabase() {
        return database;
    }
}
