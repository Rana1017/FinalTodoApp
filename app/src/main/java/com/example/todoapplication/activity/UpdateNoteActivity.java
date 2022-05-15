package com.example.todoapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todoapplication.R;
import com.example.todoapplication.databinding.ActivityAddBinding;
import com.example.todoapplication.databinding.ActivityUpdateNoteBinding;
import com.example.todoapplication.model.Note;
import com.example.todoapplication.viewmodel.NotesViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Objects;

public class UpdateNoteActivity extends AppCompatActivity {

    ActivityUpdateNoteBinding binding;
    NotesViewModel notesViewModel;

    int intentId;
    String intentTitle;
    String intentPriority;
    String intentDescription;

    String priority = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        binding = ActivityUpdateNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        intentId = getIntent().getIntExtra("id", 0);
        intentTitle = getIntent().getStringExtra("title");
        intentDescription = getIntent().getStringExtra("description");
        intentPriority = getIntent().getStringExtra("priority");

        binding.titleUdpate.setText(intentTitle);
        binding.descriptionUpdate.setText(intentDescription);

        switch (intentPriority) {
            case "1":
                binding.highPriorityUpdate.setImageResource(R.drawable.ic_baseline_check_24);
                break;
            case "2":
                binding.midPriorityUpdate.setImageResource(R.drawable.ic_baseline_check_24);
                break;
            case "3":
                binding.lowPriorityUpdate.setImageResource(R.drawable.ic_baseline_check_24);
                break;
        }


        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        binding.highPriorityUpdate.setOnClickListener(event -> {
            binding.highPriorityUpdate.setImageResource(R.drawable.ic_baseline_check_24);
            binding.midPriorityUpdate.setImageResource(0);
            binding.lowPriorityUpdate.setImageResource(0);
            priority = "1";
        });

        binding.midPriorityUpdate.setOnClickListener(event -> {
            binding.highPriorityUpdate.setImageResource(0);
            binding.midPriorityUpdate.setImageResource(R.drawable.ic_baseline_check_24);
            binding.lowPriorityUpdate.setImageResource(0);
            priority = "2";
        });

        binding.lowPriorityUpdate.setOnClickListener(event -> {
            binding.highPriorityUpdate.setImageResource(0);
            binding.midPriorityUpdate.setImageResource(0);
            binding.lowPriorityUpdate.setImageResource(R.drawable.ic_baseline_check_24);
            priority = "3";
        });


        binding.updateNotesBtn.setOnClickListener( event -> {
            String title = binding.titleUdpate.getText().toString();
            String description = binding.descriptionUpdate.getText().toString();
            this.updateNotes(intentId, title, description);
        });

    }



    public void updateNotes(int id, String title, String description) {
        CharSequence sequence = DateFormat.format("MMMM d, yyyy", new Date().getTime());

        Note note = new Note();
        note.id = id;
        note.title = title;
        note.description = description;
        note.date = sequence.toString();
        note.priority = this.priority;
        notesViewModel.updateNotes_vm(note);

        Toast.makeText(this, "Notes updated successfully", Toast.LENGTH_SHORT).show();
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        if(item.getItemId() == R.id.delete_btn) {
            BottomSheetDialog sheetDialog = new BottomSheetDialog(UpdateNoteActivity.this);
            View view = LayoutInflater.from(UpdateNoteActivity.this).inflate(R.layout.delete_sheet, (LinearLayout) findViewById(R.id.delete_sheet_linear_layout));
            sheetDialog.setContentView(view);

            TextView yesBtn, noBtn;

            yesBtn = view.findViewById(R.id.delete_true);
            noBtn = view.findViewById(R.id.delete_false);

            yesBtn.setOnClickListener(event -> {
                notesViewModel.deleteNotes_vm(intentId);
                Toast.makeText(this, "Note deleted successfully", Toast.LENGTH_SHORT).show();
                finish();
            });

            noBtn.setOnClickListener(event -> {
                sheetDialog.dismiss();
            });

            sheetDialog.show();
        }

        return true;
    }

}