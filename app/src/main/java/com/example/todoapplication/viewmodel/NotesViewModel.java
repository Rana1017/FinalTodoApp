package com.example.todoapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todoapplication.model.Note;
import com.example.todoapplication.repository.NotesRepository;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    NotesRepository repository;
    public LiveData<List<Note>> getAllNotes;

    public NotesViewModel(@NonNull Application application) {
        super(application);

        repository = new NotesRepository(application);
        getAllNotes = repository.getAllNotes;
    }

    public void insertNotes_vm(Note note) {
        repository.insertNotes(note);
    }

    public void updateNotes_vm(Note note) {
        repository.updateNotes(note);
    }

    public void deleteNotes_vm(int id) {
        repository.deleteNotes(id);
    }

}
