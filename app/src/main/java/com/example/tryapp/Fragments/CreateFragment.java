package com.example.tryapp;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.tryapp.databinding.FragmentCreateBinding;


public class CreateFragment extends Fragment {

    private FragmentCreateBinding binding;
    private NoteViewModel viewModel;
    private Application application;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.application = (Application) context.getApplicationContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        viewModel = new ViewModelProvider(requireActivity()).get(NoteViewModel.class);

        binding.createButton.setOnClickListener((v) ->
        {
            insertData();
        });

        return view;
    }


    private void insertData() {

        String name = binding.noteName.getText().toString();
        String body = binding.noteBody.getText().toString();

        if (inputCheck(name, body)) {
            Note note = new Note(0, name.trim(), body.trim());
            viewModel.addNote(note);
            Toast.makeText(requireContext(), "Successful success!", Toast.LENGTH_SHORT).show();
            NavController controller = Navigation.findNavController(binding.getRoot());
            controller.navigate(R.id.action_createFragment_to_listFragment);
            controller.popBackStack(R.id.createFragment, true);
        } else {
            Toast.makeText(requireContext(), "There is nothing to create", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean inputCheck(String name, String body) {
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(body));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }


}