package com.geektech.mynote.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.geektech.mynote.model.NoteModel;

@Database(entities = {NoteModel.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDao getDao();
}
