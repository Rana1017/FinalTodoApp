package com.example.todoapplication.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.example.todoapplication.R;
import com.example.todoapplication.adaptar.NotesAdapter;
import com.example.todoapplication.model.Note;
import com.example.todoapplication.viewmodel.NotesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addNotesBtn;
    NotesViewModel notesViewModel;
    RecyclerView recyclerView;
    NotesAdapter notesAdapter;
    SearchView searchView;
    LinearLayout notesNotFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNotesBtn = findViewById(R.id.add_note);
        searchView = findViewById(R.id.search_view_bar);
        notesNotFound = findViewById(R.id.notes_not_found);
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        addNotesBtn.setOnClickListener(event -> {
            startActivity(new Intent(MainActivity.this, AddActivity.class));
        });

        recyclerView = findViewById(R.id.notes_recycler_view);

        notesViewModel.getAllNotes.observe(this, notes -> {

            if(notes.size() > 0) {
                notesNotFound.setVisibility(View.GONE);
            }

            if(notes.size() <= 0 && notesNotFound.getVisibility() == View.GONE) {
                notesNotFound.setVisibility(View.VISIBLE);
            }

            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
            notesAdapter = new NotesAdapter(MainActivity.this, notes);
            recyclerView.setAdapter(notesAdapter);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextChange(String s) {
                notesViewModel.getAllNotes.observe(MainActivity.this, notes -> {
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
                    List<Note> noteList = notes.stream().filter(note -> note.title.contains(s)).collect(Collectors.toList());
                    notesAdapter = new NotesAdapter(MainActivity.this, noteList);
                    recyclerView.setAdapter(notesAdapter);
                });
                return true;
            }
        });
    }
}