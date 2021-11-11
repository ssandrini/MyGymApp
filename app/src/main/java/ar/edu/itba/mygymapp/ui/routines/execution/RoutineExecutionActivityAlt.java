package ar.edu.itba.mygymapp.ui.routines.execution;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import ar.edu.itba.mygymapp.backend.models.Cycle;
import ar.edu.itba.mygymapp.backend.models.CycleExercise;
import ar.edu.itba.mygymapp.backend.models.Routine;
import ar.edu.itba.mygymapp.backend.store.RoutineStore;
import ar.edu.itba.mygymapp.databinding.RoutineExecutionAltBinding;
import ar.edu.itba.mygymapp.ui.exercises.ExercisesAdapter;

public class RoutineExecutionActivityAlt extends AppCompatActivity {

    private RoutineExecutionAltBinding binding;
    private View root;
    private Routine routine;
    private ExercisesAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = RoutineExecutionAltBinding.inflate(getLayoutInflater());
        root = binding.getRoot();
        setContentView(root);

        routine = RoutineStore.getRoutines().get(0);

        setCurrentExercise(routine.getCycles().get(0).getExercises().get(0));

        ArrayList<CycleExercise> exercises = new ArrayList<>();
        for (Cycle cycle : routine.getCycles()) {
            exercises.addAll(cycle.getExercises());
        }
        exercises.remove(0);
        adapter = new ExercisesAdapter(exercises);

        binding.queueRecView.setLayoutManager(new LinearLayoutManager(this));
        binding.queueRecView.setAdapter(adapter);
    }

    private void setCurrentExercise(CycleExercise exercise) {
        binding.name.setText(exercise.getName());
        binding.type.setText(exercise.getType());
        StringBuilder s1 = new StringBuilder();
        s1.append(String.valueOf(exercise.getDuration()));
        s1.append("''");
        binding.currentDuration.setText(s1.toString());
        StringBuilder s2 = new StringBuilder();
        s2.append("x");
        s2.append(exercise.getRepetitions());
        binding.currentReps.setText(s2.toString());
    }
}
