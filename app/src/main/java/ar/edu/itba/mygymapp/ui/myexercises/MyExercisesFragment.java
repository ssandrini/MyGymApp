package ar.edu.itba.mygymapp.ui.myexercises;

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
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;

import ar.edu.itba.mygymapp.databinding.FragmentMyExercisesBinding;
import ar.edu.itba.mygymapp.ui.exercises.Exercise;
import ar.edu.itba.mygymapp.ui.exercises.ExercisesAdapter;

public class MyExercisesFragment extends Fragment {

    private MyExercisesViewModel myExercisesViewModel;
    private FragmentMyExercisesBinding binding;
    private ExercisesAdapter exercisesAdapter;
    private ArrayList<Exercise> exercises = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myExercisesViewModel =
                new ViewModelProvider(this).get(MyExercisesViewModel.class);

        binding = FragmentMyExercisesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        exercises.add(new Exercise("Elongacion", "Descanso", "Ejercicio de pecho"));
        exercises.add(new Exercise("Dominadas", "Ejercicio", "Ejercicio de espalda"));
        exercises.add(new Exercise("Elongacion", "Descanso", "Ejercicio de piernas"));
        exercises.add(new Exercise("Flexiones", "Ejercicio", "Ejercicio de pecho"));
        exercises.add(new Exercise("Elongacion", "Descanso", "Ejercicio de pecho"));
        exercises.add(new Exercise("Dominadas", "Ejercicio", "Ejercicio de espalda"));
        exercises.add(new Exercise("Elongacion", "Descanso", "Ejercicio de piernas"));
        exercises.add(new Exercise("Flexiones", "Ejercicio", "Ejercicio de pecho"));
        exercises.add(new Exercise("Elongacion", "Descanso", "Ejercicio de pecho"));
        exercises.add(new Exercise("Dominadas", "Ejercicio", "Ejercicio de espalda"));
        exercises.add(new Exercise("Elongacion", "Descanso", "Ejercicio de piernas"));
        exercises.add(new Exercise("Flexiones", "Ejercicio", "Ejercicio de pecho"));

        exercisesAdapter = new ExercisesAdapter(exercises);
        binding.myexsRecView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.myexsRecView.setAdapter(exercisesAdapter);

//        final TextView textView = binding.textSlideshow;
//        myExercisesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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