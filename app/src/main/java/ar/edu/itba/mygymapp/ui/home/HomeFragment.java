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
import ar.edu.itba.mygymapp.ui.routines.Routine;
import ar.edu.itba.mygymapp.ui.routines.RoutinesAdapter;
import ar.edu.itba.mygymapp.ui.routines.RoutineActivity;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private RoutinesAdapter routinesAdapter;

    private ArrayList<Routine> routines = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        routinesAdapter = new RoutinesAdapter();
        populateRoutines();
        routinesAdapter.setRoutines(routines);

        binding.myroutinesRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.myroutinesRecView.setAdapter(routinesAdapter);


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

    private void populateRoutines() {
        routines.add(new Routine("Calistenia", "5 out of 5", "Dagos", "r1.png"));
        routines.add(new Routine("Home", "5 out of 5", "Dax", "r2.png"));
        routines.add(new Routine("Pecs killer", "5 out of 5", "Santi", "r3.png"));
        routines.add(new Routine("Legs", "5 out of 5", "Solcha", "r4.png"));
        routines.add(new Routine("Calistenia", "5 out of 5", "Dagos", "r1.png"));
        routines.add(new Routine("Home", "5 out of 5", "Dax", "r2.png"));
        routines.add(new Routine("Pecs killer", "5 out of 5", "Santi", "r3.png"));
        routines.add(new Routine("Legs", "5 out of 5", "Solcha", "r4.png"));
    }
}