package ar.edu.itba.mygymapp.ui.routines;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import ar.edu.itba.mygymapp.databinding.ActivityMainBinding;
import ar.edu.itba.mygymapp.databinding.ActivityRoutineBinding;
import ar.edu.itba.mygymapp.ui.exercises.Exercise;
import ar.edu.itba.mygymapp.ui.exercises.ExercisesAdapter;

public class RoutineActivity extends AppCompatActivity {

    private ActivityRoutineBinding binding;
    private ExercisesAdapter exercisesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRoutineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise("Flexiones de brazo", "Ejercicio", "Para el pecho"));
        exercises.add(new Exercise("Dominadas", "Ejercicio", "Para la espalda"));

        exercisesAdapter = new ExercisesAdapter(exercises);
        binding.exercisesRecView.setLayoutManager(new LinearLayoutManager(this));
        binding.exercisesRecView.setAdapter(exercisesAdapter);
    }
}
