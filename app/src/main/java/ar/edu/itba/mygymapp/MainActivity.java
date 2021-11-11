package ar.edu.itba.mygymapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.widget.SearchView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ar.edu.itba.mygymapp.backend.models.Cycle;
import ar.edu.itba.mygymapp.backend.models.CycleExercise;
import ar.edu.itba.mygymapp.backend.models.Routine;
import ar.edu.itba.mygymapp.backend.store.RoutineStore;
import ar.edu.itba.mygymapp.backend.store.UserStore;
import ar.edu.itba.mygymapp.databinding.ActivityMainBinding;
import ar.edu.itba.mygymapp.ui.routines.RoutineActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "AppBar";

    private AppBarConfiguration mAppBarConfiguration, tAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBar.toolbar);

        populateRoutines();

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        View headerView = navigationView.getHeaderView(0);
        ImageView userImageView = headerView.findViewById(R.id.userImageView);
//        userImageView.setImageResource(R.drawable.logo);
//        userImageView.setImageURI(UserStore.getUser().getAvatarUrl());
        TextView headerText = headerView.findViewById(R.id.usernameHeader);


        Glide.with(this).asBitmap().load(UserStore.getUser().getAvatarUrl()).placeholder(R.drawable.avatar).into(userImageView);
        headerText.setText(UserStore.getUser().getUsername());
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_myroutines, R.id.nav_favourites, R.id.nav_profile)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItem optionsItem = menu.findItem(R.id.action_options);
        optionsItem.setVisible(false);
        searchItem.setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void goToRoutineActivity(View view) {

    }

    private void populateRoutines() {
        ArrayList<Routine> routines = new ArrayList<Routine>();
        routines.add(new Routine(0, "Boxeo", "Get those moves", 3.3, true, "rookie", null, null, null, true));
        routines.add(new Routine(0, "Home", "Get those moves", 2.7, true, "rookie", null, null, null, true));
        routines.add(new Routine(0, "Calistenia", "Get those moves", 4.3, true, "rookie", null, null, null, true));
        routines.add(new Routine(0, "HIIT", "Get those moves", 1.5, true, "rookie", null, null, null, true));

        ArrayList<CycleExercise> exercises = new ArrayList<>();
        exercises.add(new CycleExercise("Flexiones", "Ejercicio", "Para el pecho", 0, 1, 20, 10, null));
        exercises.add(new CycleExercise("Dominadas", "Ejercicio", "Para la espalda", 1, 2, 30, 5, null));
        ArrayList<Cycle> cycles = new ArrayList<>();
        cycles.add(new Cycle(0, "Ciclo A", "Calentando", "Calentamiento", 1, 10, null, exercises));
        cycles.add(new Cycle(1, "Ciclo B", "Calentando2", "Entrenando", 2, 10, null, exercises));
        cycles.add(new Cycle(2, "Ciclo C", "Calentando3", "Enfriamiento", 3, 10, null, exercises));

        for (Routine routine : routines) {
            routine.setCycles(cycles);
        }

        RoutineStore.setRoutines(routines);
    }
}