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

import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

import ar.edu.itba.mygymapp.backend.models.Routine;
import ar.edu.itba.mygymapp.R;
import ar.edu.itba.mygymapp.databinding.ActivityRoutineBinding;
import ar.edu.itba.mygymapp.backend.models.Cycle;
import ar.edu.itba.mygymapp.ui.cycles.CyclesAdapter;
import ar.edu.itba.mygymapp.backend.models.CycleExercise;

import ar.edu.itba.mygymapp.ui.exercises.ExercisesAdapter;
import ar.edu.itba.mygymapp.ui.scheduler.SchedulerActivity;

public class RoutineActivity extends AppCompatActivity {

    static final private String ID_PARENT_EXTRA = "com.example.fithub_mobile.ID_PARENT";

    private ActivityRoutineBinding binding;
    private ExercisesAdapter exercisesAdapter;
    private CyclesAdapter cyclesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRoutineBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        CollapsingToolbarLayout cToolbar = binding.collapsingToolbarLayout;
        Toolbar toolbar = binding.toolbarMain;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setContentView(root);

        Intent i = getIntent();
        Routine routine = (Routine) i.getSerializableExtra("routineObject");

        binding.rName.setText(routine.getName());
        binding.rDifficulty.setText(routine.getDifficulty());
        binding.rScore.setRating(routine.getScore().floatValue());
        binding.rDetail.setText(routine.getDetail());
        binding.collapsingToolbarLayout.setTitle(routine.getName());

        ArrayList<CycleExercise> exercises = new ArrayList<>();
        exercises.add(new CycleExercise("Flexiones", "Ejercicio", "Para el pecho", 0, 1, 2, 3, null));
        exercises.add(new CycleExercise("Dominadas", "Ejercicio", "Para la espalda", 1, 2, 3, 4, null));
        ArrayList<Cycle> cycles = new ArrayList<>();
        cycles.add(new Cycle(0, "Ciclo A", "Calentando", "Calentamiento", 1, 10, null, exercises));
        cycles.add(new Cycle(1, "Ciclo B", "Calentando2", "Entrenando", 2, 10, null, exercises));
        cycles.add(new Cycle(2, "Ciclo C", "Calentando3", "Enfriamiento", 3, 10, null, exercises));

        routine.setCycles(cycles);

        cyclesAdapter = new CyclesAdapter(routine.getCycles());
        binding.cyclesRecView.setLayoutManager(new LinearLayoutManager(this));
        binding.cyclesRecView.setAdapter(cyclesAdapter);

        binding.calendarBtn.setOnClickListener(view -> {
            Intent calIntent = new Intent(this, SchedulerActivity.class);
            calIntent.putExtra(ID_PARENT_EXTRA, 2);
            startActivity(calIntent);
        });


        binding.reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    public void openDialog() {
        ReviewDialog reviewDialog = new ReviewDialog();
        reviewDialog.show(getSupportFragmentManager(), "Review Dialog");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_routine, menu);

        MenuItem favItem = menu.findItem(R.id.action_fav);
        MenuItem shareItem = menu.findItem(R.id.action_share);

        favItem.setVisible(true);
        shareItem.setVisible(true);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
