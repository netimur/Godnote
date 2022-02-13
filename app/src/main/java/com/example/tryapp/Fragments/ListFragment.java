package com.example.tryapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.tryapp.data.NoteViewModel;
import com.example.tryapp.databinding.FragmentListBinding;


public class ListFragment extends Fragment {

    private FragmentListBinding binding;
    private NoteViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        CustomAdapter adapter = new CustomAdapter(requireContext());
        binding.recyclerView.setAdapter(adapter);


        viewModel = new ViewModelProvider(requireActivity()).get(NoteViewModel.class);


        viewModel.getReadAllData().observe(getViewLifecycleOwner(), note ->
                {
                    adapter.setNotes(note);
                    if (viewModel.getReadAllData().getValue().size() == 0) {
                        binding.emptyLabel.setVisibility(View.VISIBLE);
                    } else binding.emptyLabel.setVisibility(View.GONE);
                }
        );

        binding.addButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_listFragment_to_createFragment);
        });


        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}