package com.example.todoapplication.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todoapplication.model.Note;

import java.util.List;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM note")
    LiveData<List<Note>> getAll();

    @Insert
    void insertNotes(Note... notes);

    @Query("DELETE FROM note WHERE note.id = :id")
    void deleteNote(int id);

    @Update
    void updateNotes(Note... notes);
}
