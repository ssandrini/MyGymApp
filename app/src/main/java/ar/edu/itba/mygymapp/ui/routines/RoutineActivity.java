package ar.edu.itba.mygymapp.ui.routines;

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

import ar.edu.itba.mygymapp.R;
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
        View root = binding.getRoot();
        Toolbar toolbar = binding.toolbarMain;
        setSupportActionBar(toolbar);
        setContentView(root);
        ArrayList<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise("Flexiones de brazo", "Ejercicio", "Para el pecho"));
        exercises.add(new Exercise("Dominadas", "Ejercicio", "Para la espalda"));

        exercisesAdapter = new ExercisesAdapter(exercises);
        binding.exercisesRecView.setLayoutManager(new LinearLayoutManager(this));
        binding.exercisesRecView.setAdapter(exercisesAdapter);
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
