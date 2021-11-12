package ar.edu.itba.mygymapp.ui.routines.execution;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import ar.edu.itba.mygymapp.backend.App;
import ar.edu.itba.mygymapp.backend.models.Cycle;
import ar.edu.itba.mygymapp.backend.models.CycleExercise;
import ar.edu.itba.mygymapp.backend.models.Routine;
import ar.edu.itba.mygymapp.backend.store.RoutineStore;
import ar.edu.itba.mygymapp.databinding.RoutineExecutionAltBinding;
import ar.edu.itba.mygymapp.ui.exercises.ExercisesQueueAdapter;


public class RoutineExecutionActivityAlt extends AppCompatActivity {

    private RoutineExecutionAltBinding binding;
    private View root;
    private Routine routine;
    private Integer routineId;
    private ExercisesQueueAdapter adapter;
    private ArrayList<CycleExercise> exercises;
    private int currentExercise = 0;
    private App app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = RoutineExecutionAltBinding.inflate(getLayoutInflater());
        root = binding.getRoot();
        setContentView(root);

        app = (App) this.getApplication();
        Intent i = getIntent();
        routineId = i.getIntExtra("routineId", -1);
        routine = app.getRoutineRepository().getCacheRoutineById(routineId);

        setCurrentExercise(routine.getCycles().get(0).getExercises().get(0));

        exercises = new ArrayList<>();
        for (Cycle cycle : routine.getCycles()) {
            exercises.addAll(cycle.getExercises());
        }
        CycleExercise aux = exercises.get(0);
        exercises.remove(0);
        adapter = new ExercisesQueueAdapter(exercises);
        exercises.add(0,aux);
        binding.queueRecView.setLayoutManager(new LinearLayoutManager(this));
        binding.queueRecView.setAdapter(adapter);

        binding.previousBtn.setOnClickListener(view -> {
            if(currentExercise > 0) {
                adapter.previous(exercises.get(currentExercise));
                currentExercise--;
                setCurrentExercise(exercises.get(currentExercise));
            }
        });

        binding.nextBtn.setOnClickListener(view -> {

            if (currentExercise < exercises.size() -1) {

                currentExercise++;
                setCurrentExercise(adapter.next());
            }
        });
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
