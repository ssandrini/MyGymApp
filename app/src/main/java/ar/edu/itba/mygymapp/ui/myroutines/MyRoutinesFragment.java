package ar.edu.itba.mygymapp.ui.myroutines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import ar.edu.itba.mygymapp.databinding.FragmentMyRoutinesBinding;
import ar.edu.itba.mygymapp.ui.routines.RoutinesAdapter;

public class MyRoutinesFragment extends Fragment {

    private MyRoutinesViewModel myRoutinesViewModel;
    private FragmentMyRoutinesBinding binding;
    private RoutinesAdapter routinesAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myRoutinesViewModel =
                new ViewModelProvider(this).get(MyRoutinesViewModel.class);

        binding = FragmentMyRoutinesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        routinesAdapter = new RoutinesAdapter();

        binding.destacadasRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.destacadasRecView.setAdapter(routinesAdapter);

//        final TextView textView = binding.textGallery;
//        myRoutinesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}