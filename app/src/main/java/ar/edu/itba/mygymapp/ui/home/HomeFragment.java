package ar.edu.itba.mygymapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import ar.edu.itba.mygymapp.databinding.FragmentHomeBinding;
import ar.edu.itba.mygymapp.ui.exercises.Exercise;
import ar.edu.itba.mygymapp.ui.exercises.ExercisesAdapter;
import ar.edu.itba.mygymapp.ui.routines.RoutinesAdapter;
import ar.edu.itba.mygymapp.ui.routines.RoutineActivity;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private RoutinesAdapter routinesAdapter;
    private ExercisesAdapter exercisesAdapter;
    private ArrayList<Exercise> exercises = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        routinesAdapter = new RoutinesAdapter();

        binding.destacadasRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.destacadasRecView.setAdapter(routinesAdapter);

        exercises.add(new Exercise("Elongacion", "Descanso", "Ejercicio de pecho"));
        exercises.add(new Exercise("Dominadas", "Ejercicio", "Ejercicio de espalda"));
        exercises.add(new Exercise("Elongacion", "Descanso", "Ejercicio de piernas"));
        exercises.add(new Exercise("Flexiones", "Ejercicio", "Ejercicio de pecho"));

        exercisesAdapter = new ExercisesAdapter(exercises);
        binding.exercisesRecView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.exercisesRecView.setAdapter(exercisesAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void goToRoutineActivity() {
        Intent intent = new Intent(getContext(), RoutineActivity.class);
        startActivity(intent);
    }
}