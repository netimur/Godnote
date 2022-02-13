package com.example.tryapp;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tryapp.data.Note;
import com.example.tryapp.data.NoteViewModel;
import com.example.tryapp.databinding.FragmentNoteViewBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class NoteViewFragment extends Fragment {

    private FragmentNoteViewBinding binding;
    private NoteViewModel viewModel;
    private Note note;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        binding = FragmentNoteViewBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(requireActivity()).get(NoteViewModel.class);

        note = NoteViewFragmentArgs.fromBundle(getArguments()).getNote();
        binding.noteNameView.setText(note.getNoteName());
        binding.noteBodyView.setText(note.getNoteBody());
        binding.goToUpdate.setOnClickListener((v -> {
            NoteViewFragmentDirections.ActionNoteViewFragmentToUpdateFragment act =
                    NoteViewFragmentDirections.actionNoteViewFragmentToUpdateFragment(note);
            NavController controller = Navigation.findNavController(view);
            controller.navigate(act);

        }));
        binding.deleteButton.setOnClickListener(v -> {
            deleteUser();
        });

        return view;

    }


    private void deleteUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Deleting");
        builder.setMessage("Do you really wanna delete this: " + note.getNoteName() + "?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            viewModel.deleteNote(note);
            Toast.makeText(requireContext(), "Successful success", Toast.LENGTH_SHORT).show();
            NavController controller = Navigation.findNavController(binding.getRoot());
            controller.navigate(R.id.action_noteViewFragment_to_listFragment);
            controller.popBackStack(R.id.noteViewFragment, true);
        });
        builder.setNegativeButton("No", null);
        builder.create().show();


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}