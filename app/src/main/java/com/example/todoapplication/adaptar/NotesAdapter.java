package com.example.todoapplication.adaptar;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapplication.activity.MainActivity;
import com.example.todoapplication.R;
import com.example.todoapplication.activity.UpdateNoteActivity;
import com.example.todoapplication.model.Note;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    MainActivity mainActivity;
    List<Note> notes;

    public NotesAdapter(MainActivity mainActivity, List<Note> notes) {
        this.mainActivity = mainActivity;
        this.notes = notes;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(mainActivity).inflate(R.layout.notes_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = notes.get(position);

        switch (note.priority) {
            case "1":
                holder.priority.setBackgroundResource(R.drawable.red_dot);
                break;
            case "2":
                holder.priority.setBackgroundResource(R.drawable.yellow_dot);
                break;
            case "3":
                holder.priority.setBackgroundResource(R.drawable.green_dot);
                break;
        }

        holder.title.setText(note.title);
        holder.description.setText(note.description);
        holder.date.setText(note.date);

        holder.itemView.setOnClickListener(event -> {
            Intent intent = new Intent(mainActivity, UpdateNoteActivity.class);
            intent.putExtra("id", note.id);
            intent.putExtra("title", note.title);
            intent.putExtra("description", note.description);
            intent.putExtra("priority", note.priority);
            mainActivity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    static class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView title, date, description;
        View priority;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.notesTitle);
            description = itemView.findViewById(R.id.notesDescription);
            date = itemView.findViewById(R.id.notesDate);
            priority = itemView.findViewById(R.id.priority);
        }
    }
}
