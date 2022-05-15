package com.example.todoapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.example.todoapplication.R;
import com.example.todoapplication.databinding.ActivityAddBinding;
import com.example.todoapplication.model.Note;
import com.example.todoapplication.viewmodel.NotesViewModel;

import java.util.Date;
import java.util.Objects;

public class AddActivity extends AppCompatActivity {

    ActivityAddBinding binding;
    String title;
    String description;
    NotesViewModel notesViewModel;
    String priority = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        binding.highPriority.setOnClickListener(event -> {
            binding.highPriority.setImageResource(R.drawable.ic_baseline_check_24);
            binding.midPriority.setImageResource(0);
            binding.lowPriority.setImageResource(0);
            priority = "1";
        });

        binding.midPriority.setOnClickListener(event -> {
            binding.highPriority.setImageResource(0);
            binding.midPriority.setImageResource(R.drawable.ic_baseline_check_24);
            binding.lowPriority.setImageResource(0);
            priority = "2";
        });

        binding.lowPriority.setOnClickListener(event -> {
            binding.highPriority.setImageResource(0);
            binding.midPriority.setImageResource(0);
            binding.lowPriority.setImageResource(R.drawable.ic_baseline_check_24);
            priority = "3";
        });

        binding.addNotesBtn.setOnClickListener( event -> {
            title = binding.notesTitle.getText().toString();
            description = binding.notesDescription.getText().toString();
            this.createNotes(title, description);
        });


    }


    public void createNotes(String title, String description) {

        CharSequence sequence = DateFormat.format("MMMM d, yyyy", new Date().getTime());

        Note note = new Note();
        note.title = title;
        note.description = description;
        note.date = sequence.toString();
        note.priority = this.priority;
        notesViewModel.insertNotes_vm(note);

        Toast.makeText(this, "Notes created successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}