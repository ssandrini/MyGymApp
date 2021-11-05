package ar.edu.itba.mygymapp.ui.routines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import ar.edu.itba.mygymapp.R;
import ar.edu.itba.mygymapp.databinding.FragmentRoutinesBinding;

public class RoutinesFragment extends Fragment {
    private FragmentRoutinesBinding binding;
    private RoutinesViewModel routinesViewModel;
    private RoutinesAdapter routinesAdapter;
    private ArrayList<Routine> routines = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        routinesViewModel = new ViewModelProvider(this).get(RoutinesViewModel.class);

        binding = FragmentRoutinesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        routinesAdapter = new RoutinesAdapter();
        populateRoutines();
        routinesAdapter.setRoutines(routines);

        binding.routinesRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.routinesRecView.setAdapter(routinesAdapter);

        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.action_search);
        if(searchItem != null)
            searchItem.setVisible(true);
        //super.onPrepareOptionsMenu(menu);  CREO que esta línea no va.
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