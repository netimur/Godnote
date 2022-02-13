package com.example.tryapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tryapp.data.Note;
import com.example.tryapp.data.NoteViewModel;
import com.example.tryapp.databinding.FragmentUpdateBinding;

public class UpdateFragment extends Fragment {
    private FragmentUpdateBinding binding;
    private Note note;
    private NoteViewModel viewModel;

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentUpdateBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        note = UpdateFragmentArgs.fromBundle(getArguments()).getNote();
        binding.noteNameEditText.setText(note.getNoteName());
        binding.noteBodyEditText.setText(note.getNoteBody());

        viewModel = new ViewModelProvider(requireActivity()).get(NoteViewModel.class);


        binding.updateButton.setOnClickListener((v -> {
            if (thereAreChanges(binding.noteNameEditText.getText().toString(), binding.noteBodyEditText.getText().toString())) {
                updateNote();

            } else {
                Toast.makeText(getContext(), "There is no changes", Toast.LENGTH_SHORT).show();
            }
        }));
        return view;


    }

    private boolean thereAreChanges(String newName, String newBody) {
        if (newName.equals(note.getNoteName()) && newBody.equals(note.getNoteBody()))
            return false;
        else
            return true;
    }

    private void updateNote() {
        String newName = binding.noteNameEditText.getText().toString();
        String newBody = binding.noteBodyEditText.getText().toString();
        if (inputCheck(newName, newBody)) {
            Note updatedNote = new Note(
                    note.getId(),
                    binding.noteNameEditText.getText().toString().trim(),
                    binding.noteBodyEditText.getText().toString().trim()
            );

            viewModel.updateNote(updatedNote);
            Toast.makeText(getContext(), "Successful success", Toast.LENGTH_SHORT).show();
            NavController controller = Navigation.findNavController(binding.getRoot());

            UpdateFragmentDirections.ActionUpdateFragmentToNoteViewFragment act =
                    UpdateFragmentDirections.actionUpdateFragmentToNoteViewFragment(updatedNote);
            controller.navigate(act);


           /* controller.navigate(R.id.action_updateFragment_to_listFragment);*/
            /*controller.popBackStack(R.id.updateFragment, true);*/
            //TODO Короче надо дописать, чтобы измененная нота переходила аргументом во вьюшку
        }
    }

    private boolean inputCheck(String name, String body) {
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(body));
    }
}