package com.example.tryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tryapp.data.Note;
import com.example.tryapp.databinding.NoteItemBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<Note> notes = Collections.emptyList();
    private LayoutInflater inflater;

    CustomAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        NoteItemBinding binding = NoteItemBinding.inflate(this.inflater, parent, false);

        return new CustomViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        Note currentNote = notes.get(position);
        holder.bind(currentNote.getNoteName(), currentNote.getNoteBody(), (v) -> {
            ListFragmentDirections.ActionListFragmentToNoteViewFragment act =
                    ListFragmentDirections.actionListFragmentToNoteViewFragment(currentNote);
            Navigation.findNavController(holder.itemView).navigate(act);
        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        private NoteItemBinding b;
        private View.OnClickListener listener;

        public CustomViewHolder(NoteItemBinding b) {
            super(b.getRoot());
            this.b = b;
        }

        void bind(String name, String body, View.OnClickListener listener) {
            b.noteName.setText(name);
            b.noteBody.setText(body);
            b.noteItem.setOnClickListener(listener);
        }
    }

    public void setNotes(List<Note> note) {
        this.notes = note;
        notifyDataSetChanged();
    }

}
