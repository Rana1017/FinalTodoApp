package com.example.todoapplication.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.todoapplication.dao.NotesDao;
import com.example.todoapplication.database.NotesDatabase;
import com.example.todoapplication.model.Note;

import java.util.List;

public class NotesRepository {

    public NotesDao notesDao;
    public LiveData<List<Note>> getAllNotes;

    public NotesRepository(Application application) {
        NotesDatabase database = NotesDatabase.getDatabaseInstance(application);
        notesDao = database.notesDao();
        getAllNotes = notesDao.getAll();
    }

    public void insertNotes(Note note) {
        notesDao.insertNotes(note);
    }

    public void deleteNotes(int id) {
        notesDao.deleteNote(id);
    }

    public void updateNotes(Note note) {
        notesDao.updateNotes(note);
    }
}
