package ar.edu.itba.mygymapp.ui.routines;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import ar.edu.itba.mygymapp.backend.models.Routine;
import ar.edu.itba.mygymapp.R;
import ar.edu.itba.mygymapp.databinding.ActivityRoutineBinding;
import ar.edu.itba.mygymapp.backend.models.Cycle;
import ar.edu.itba.mygymapp.ui.cycles.CyclesAdapter;
import ar.edu.itba.mygymapp.backend.models.CycleExercise;

import ar.edu.itba.mygymapp.ui.exercises.ExercisesAdapter;

public class RoutineActivity extends AppCompatActivity {

    private ActivityRoutineBinding binding;
    private ExercisesAdapter exercisesAdapter;
    private CyclesAdapter cyclesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRoutineBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        Toolbar toolbar = binding.toolbarMain;
        setSupportActionBar(toolbar);
        setContentView(root);

        Intent i = getIntent();
        Routine routine = (Routine) i.getSerializableExtra("routineObject");

        binding.rName.setText(routine.getName());
        binding.rDifficulty.setText(routine.getDifficulty());
        binding.rScore.setRating(routine.getScore().floatValue());
        binding.rDetail.setText(routine.getDetail());

        ArrayList<CycleExercise> exercises = new ArrayList<>();
        exercises.add(new CycleExercise("Flexiones", "Ejercicio", "Para el pecho", 0, 1, 2, 3, null));
        exercises.add(new CycleExercise("Dominadas", "Ejercicio", "Para la espalda", 1, 2, 3, 4, null));
        ArrayList<Cycle> cycles = new ArrayList<>();
        cycles.add(new Cycle(0, "Ciclo A", "Calentando", "Calentamiento", 1, 10, null, exercises));
        cycles.add(new Cycle(1, "Ciclo B", "Calentando2", "Entrenando", 2, 10, null, exercises));
        cycles.add(new Cycle(2, "Ciclo C", "Calentando3", "Enfriamiento", 3, 10, null, exercises));


        cyclesAdapter = new CyclesAdapter(cycles);
        binding.cyclesRecView.setLayoutManager(new LinearLayoutManager(this));
        binding.cyclesRecView.setAdapter(cyclesAdapter);

//        exercisesAdapter = new ExercisesAdapter(exercises);
//        binding.exercisesRecView.setLayoutManager(new LinearLayoutManager(this));
//        binding.exercisesRecView.setAdapter(exercisesAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_routine, menu);
        MenuItem favItem = menu.findItem(R.id.action_fav);
        MenuItem shareItem = menu.findItem(R.id.action_share);

        favItem.setVisible(true);
        shareItem.setVisible(true);

        return super.onCreateOptionsMenu(menu);
    }

}
